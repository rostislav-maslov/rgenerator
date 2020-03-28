import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import ReactJson from 'react-json-view'

import GeneratorApi from "../../services/Generator";

class HomeScene extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: {},
                exampleString: "{}",
            },
            apiData: {
                catalogsResponse: [],
                productsResponse: []
            },
            data: {
                filesToUpload: [],
                generator: null
            }
        };
    }

    componentDidMount() {
        this.update()
    }

    update = () => {

    }



    onChange = (e) => {
        const files = [...e.target.files]
        let filesToUpload = this.state.data.filesToUpload

        files.forEach((file) => {
            filesToUpload.push({
                path: file.webkitRelativePath,
                file: file,
                didUpload: false
            })
        })

        let data = this.state.data
        data.filesToUpload = filesToUpload
        this.setState({data: data})
    }

    createGenerator = () => {
        let self = this
        GeneratorApi.create(
            this.state.viewData.title,
            this.state.viewData.description,
            JSON.stringify(this.state.viewData.example)
        ).then((result) => {
            result.json().then((responseJson) => {
                const json = responseJson.result
                let data = this.state.data
                data.generator = json
                this.setState({data: data}, () => {
                    self.uploadNextFile()
                })
            })
        })
    }

    uploadNextFile = () => {
        let data = this.state.data
        let self = this
        let hasFile = false

        for (let i = 0; i < data.filesToUpload.length; i++) {
            if (data.filesToUpload[i].didUpload == false) {
                hasFile = true
                GeneratorApi.uploadFile(
                    data.generator.id,
                    data.filesToUpload[i].path,
                    data.filesToUpload[i].file
                ).then((response) => {
                    data.filesToUpload[i].didUpload = true
                    this.setState({data: data}, () => {
                        self.uploadNextFile()
                    })
                })
                break;
            }
        }

        if (hasFile == false) {
            // все файлы загрузились, нужно перейти на list
            window.location = `/template-result/${data.generator.id}`
        }
    }

    onSubmit = (e) => {
        e.preventDefault();
        this.createGenerator()
        return false;
    }

    onUpdateJson = (e) => {
        let viewData = this.state.viewData;
        viewData.example = e.updated_src
        viewData.exampleString = JSON.stringify(e.updated_src, null, 4)
        this.setState({viewData:viewData})
    }

    onChangeInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;

        let viewData = this.state.viewData;
        viewData[name] = value
        this.setState({viewData: viewData})
    }

    onChangeExample = (e) => {
        try {
            const value = JSON.parse(e.target.value);

            let viewData = this.state.viewData;
            viewData.example = value
            viewData.exampleString = e.target.value
            this.setState({viewData: viewData})
        }catch (ex) {
            let viewData = this.state.viewData;
            viewData.exampleString = e.target.value
            this.setState({viewData: viewData})
        }
    }

    render() {
        return (
            <section>
                <div className="container">
                    <h1>Upload template</h1>
                    <form onSubmit={this.onSubmit}>

                        <div className="row">
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="inpTitle">Title</label>
                                    <input type="text" className="form-control" id="inpTitle"
                                           name={'title'} onChange={this.onChangeInput}
                                           value={this.state.viewData.title}/>

                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="txtDescription">Description</label>
                                    <textarea className="form-control" id="txtDescription" name={'description'}
                                              onChange={this.onChangeInput} value={this.state.viewData.description}/>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="txtExample">Example json with your data</label>
                                    <textarea className="form-control" id="txtExample" name={'example'}
                                              onChange={this.onChangeExample} value={this.state.viewData.exampleString}/>

                                    <ReactJson
                                        src={this.state.viewData.example}
                                        collapsed={false}
                                        enableClipboard={true}
                                        onEdit={this.onUpdateJson}
                                        onAdd={this.onUpdateJson}
                                        onDelete={this.onUpdateJson}
                                    />
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="exampleFormControlFile1">Dir with your template</label>
                                    <input type="file" className="form-control-file" id="exampleFormControlFile1"
                                           name="fileList2" directory="" webkitdirectory="" onChange={this.onChange}/>
                                </div>
                            </div>
                        </div>
                        {this.state.data.filesToUpload.length > 0 ?
                            (<div className="row">
                                <div className="col">
                                    <h2>Files</h2>
                                    <div>
                                        {this.state.data.filesToUpload.map((file, idx) => {
                                            return (<div key={idx}>[{file.didUpload.toString()}] - {file.path}</div>)
                                        })}
                                    </div>
                                </div>
                            </div>) : false}
                        <div className="row">
                            <div className="col">
                                <button type="submit" className="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </form>


                </div>
            </section>
        );
    }
}


export default HomeScene;
