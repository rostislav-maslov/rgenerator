import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";
import TemplateResultApi from "../../../gateways/services/TemplateResult";
import {withRouter} from 'react-router-dom';
import {FILE, FILE_ATTACH} from "../../../gateways/services/Const";
import ReactJson from 'react-json-view'
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";

import {toast, ToastContainer} from 'react-toastify';


class EditFileListScene extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
            },
            apiData: {
                generator: {
                    title: '',
                    id: null,
                    files: []
                },
                templateResult: null
            },
            data: {
                filesToUpload: []
            }
        };
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        this.update()
    }

    update = () => {
        this.loadGenerator(this.props.match.params.id)
    }

    loadGenerator = (id) => {
        let self = this
        GeneratorApi.findById(id)
            .then((response) => {
                response.json().then(result => {
                    let apiData = this.state.apiData
                    apiData.generator = result.result
                    let viewData = this.state.viewData
                    viewData.title = apiData.generator.title
                    viewData.description = apiData.generator.description
                    try {
                        viewData.example = JSON.parse(apiData.generator.example)
                    } catch (e) {

                    }
                    viewData.exampleString = apiData.generator.example

                    self.setState({
                        apiData: apiData,
                        viewData: viewData
                    })
                })
            })
    }

    onSubmit = (e) => {
        e.preventDefault()
        this.uploadNextFile()
        return false
    }

    uploadNextFile = () => {
        let data = this.state.data
        let generator = this.state.apiData.generator
        let self = this
        let hasFile = false

        for (let i = 0; i < data.filesToUpload.length; i++) {
            if (data.filesToUpload[i].didUpload == false) {
                hasFile = true
                GeneratorApi.uploadFile(
                    generator.id,
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
            window.location = `/generator/${generator.id}/edit/files`
        }
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


    render() {
        return (
            <section>
                <div className="container-fluid">
                    <div className="row">
                        <LeftMenuComponent activeLink={'files'} generator={this.state.apiData.generator}/>

                        <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                            <section>
                                <div className={'container-fluid'}>
                                    <div className={'row'}>
                                        <div className={'col-10'}>
                                            <Breadcrumbs links={[
                                                {title: 'Home', url: '/'},
                                                {title: 'Generators', url: '/generator'},
                                                {
                                                    title: this.state.apiData.generator.title,
                                                    url: `/generator/${this.props.match.params.id}`
                                                },
                                                {
                                                    title: 'Files',
                                                    url: `/generator/${this.props.match.params.id}/edit/files`
                                                },
                                                {
                                                    title: 'Add',
                                                    url: `/generator/${this.props.match.params.id}/edit/files/add`
                                                },
                                            ]}/>
                                        </div>
                                        <div className={'col-2'}>
                                            <Link to={`/generator/${this.props.match.params.id}/edit/files/add`}
                                                  style={{
                                                      padding: '.69rem 1rem',
                                                      fontSize: '.875rem'
                                                  }}
                                                  className={'btn btn-primary btn-md float-right'}>
                                                Add file
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            </section>

                            <div className="container-fluid">
                                <div className={'row'}>
                                    <div className={'col'}>
                                        <h1>Add files</h1>
                                    </div>
                                </div>
                            </div>

                            <div className={'container-fluid'}>
                                <div className={'row'}>
                                    <div className={'col'}>
                                        <div
                                            className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <form onSubmit={this.onSubmit}>
                                <div className="container-fluid">
                                    <div className="row">
                                        <div className="col-md-12">
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

                                        <br/>
                                    </div>
                                </div>
                                <br/>
                                <div className={'container-fluid'}>
                                    <div className={'row'}>
                                        {this.state.data.filesToUpload.length > 0 ?
                                            (<div className="col-md-12">
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

                        </main>
                    </div>
                </div>


            </section>
        );
    }
}


export default withRouter(EditFileListScene);
