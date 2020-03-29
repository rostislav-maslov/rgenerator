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
import LeftMenuComponent from "../../components/LeftMenu";

class CreateScene extends Component {
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
        this.setState({viewData: viewData})
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
        } catch (ex) {
            let viewData = this.state.viewData;
            viewData.exampleString = e.target.value
            this.setState({viewData: viewData})
        }
    }

    render() {
        return (

            <section>
                <div className="container-fluid">
                    <div className="row">
                        <LeftMenuComponent/>

                        <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                            <section>
                                <div className="container-fluid">
                                    <div className="row">
                                        <div className="col-sm-12">
                                            <br/>
                                            <br/>
                                            <h1>Create template</h1>
                                            <br/>
                                        </div>
                                    </div>
                                </div>
                                <form onSubmit={this.onSubmit}>
                                    <div className="container-fluid">
                                        <div className="row">
                                            <div className="col-sm-12">
                                                <div className="card">
                                                    <div className="card-body">
                                                        <h5 className="card-title">Info</h5>
                                                        <div className="form-group">
                                                            <label htmlFor="inpTitle">Title</label>
                                                            <input type="text" className="form-control" id="inpTitle"
                                                                   name={'title'} onChange={this.onChangeInput}
                                                                   value={this.state.viewData.title}/>

                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="txtDescription">Description</label>
                                                            <textarea className="form-control" id="txtDescription"
                                                                      name={'description'}
                                                                      onChange={this.onChangeInput}
                                                                      value={this.state.viewData.description}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <br/>
                                    <div className="container-fluid">
                                        <div className="row">
                                            <div className="col-md-6">
                                                <div className="card">
                                                    <div className="card-body">
                                                        <h5 className="card-title">Past example of your data</h5>
                                                        <textarea className="form-control" id="txtExample"
                                                                  name={'example'}
                                                                  onChange={this.onChangeExample}
                                                                  value={this.state.viewData.exampleString}
                                                                  style={{minHeight: '50vh'}}
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="col-md-6">
                                                <div className="card">
                                                    <div className="card-body">
                                                        <h5 className="card-title">Or just create new one</h5>
                                                        <ReactJson
                                                            style={{minHeight: '50vh'}}
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
                                        </div>
                                    </div>
                                    <br/>
                                    <div className="container-fluid">
                                        <div className="row">
                                            <div className="col-md-6">
                                                <div className="card">
                                                    <div className="card-body">
                                                        <h1>Choose dir with template</h1>
                                                        <div className="form-group">
                                                            <label htmlFor="exampleFormControlFile1">Directory</label>
                                                            <input type="file" className="form-control-file"
                                                                   id="exampleFormControlFile1"
                                                                   name="fileList2" directory="" webkitdirectory=""
                                                                   onChange={this.onChange}/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            {this.state.data.filesToUpload.length > 0 ?
                                                (<div className="col-md-6">
                                                    <div className="card">
                                                        <div className="card-body">
                                                            <h2>Files</h2>
                                                            <div>
                                                                {this.state.data.filesToUpload.map((file, idx) => {
                                                                    return (<div key={idx}>[{file.didUpload.toString()}]
                                                                        - {file.path}</div>)
                                                                })}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>) : false}
                                            <br/>
                                        </div>
                                    </div>
                                    <br/>
                                    <br/>
                                    <div className="container-fluid">
                                        <div className="row">
                                            <div className="col text-center">
                                                <button type="submit" className="btn btn-primary ">Submit</button>
                                            </div>
                                        </div>
                                    </div>
                                    <br/>
                                    <br/>
                                </form>
                            </section>
                        </main>
                    </div>
                </div>


            </section>
        );
    }
}


export default CreateScene;
