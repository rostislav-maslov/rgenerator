import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../services/Generator";
import TemplateResultApi from "../../services/TemplateResult";
import {withRouter} from 'react-router-dom';
import {FILE, FILE_ATTACH} from "../../services/Const";
import ReactJson from 'react-json-view'
import LeftMenuComponent from "../../components/LeftMenu";
import Breadcrumbs from "../../components/Breadcrumbs";

import {toast, ToastContainer} from 'react-toastify';


class EditFileListScene extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
                viewResult: false
            },
            apiData: {
                generator: {
                    title: '',
                    id: null,
                    files: []
                },
                file: {
                    fileId: null,
                    path: null,
                    content: null,
                    type: null
                },
                templateResult: {
                    path: '',
                    content: ''
                }
            },
            data: {
                file: null
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

                    self.setState({
                        apiData: apiData,
                        viewData: viewData
                    }, () => {
                        self.loadFileContent(id, this.props.match.params.fileId)
                    })
                })
            })
    }

    loadFileContent = (id, fileId) => {
        let self = this
        GeneratorApi.fileContent(id, fileId).then((result) => {
            result.json().then((response) => {

                let apiData = this.state.apiData
                apiData.file = response.result

                self.setState({
                    apiData: apiData
                })
            })
        })
    }

    onChange = (e) => {
        const files = [...e.target.files]
        let fileToUpload = null
        let fileObj = files[0]

        fileToUpload = {
            path: fileObj.webkitRelativePath,
            file: fileObj,
            didUpload: false
        }

        let data = this.state.data
        data.file = fileToUpload
        this.setState({data: data})
    }

    uploadFile = () => {
        let data = this.state.data
        let apiData = this.state.apiData
        GeneratorApi.deleteFile(apiData.generator.id, apiData.file.fileId).then(
            (response) => {
                GeneratorApi.uploadFile(
                    apiData.generator.id,
                    apiData.file.path,
                    data.file.file
                ).then((response) => {
                    response.json().then((result) => {
                        window.location = `/generator/${apiData.generator.id}/edit/files/${result.result.fileId}`
                    })
                })
            }
        )
    }

    onChangePath = (e) => {
        let apiData = this.state.apiData
        apiData.file.path = e.target.value
        this.setState({apiData: apiData})
    }

    onSubmit = (e) => {
        e.preventDefault();
        this.uploadFile()
        return false;
    }

    onClickViewResult = (e) => {
        e.preventDefault();
        let viewData = this.state.viewData
        viewData.viewResult = !viewData.viewResult
        this.setState({viewData: viewData})

        let self = this

        if(viewData.viewResult == true){
            GeneratorApi.generateExample(this.state.apiData.generator.id, this.state.apiData.file.content).then(
                (response) => {
                    response.text().then((text) => {
                        let apiData = self.state.apiData
                        apiData.templateResult.content = text
                        self.setState({apiData:apiData})
                    })
                }
            )

            GeneratorApi.generateExample(this.state.apiData.generator.id, this.state.apiData.file.path).then(
                (response) => {
                    response.text().then((text) => {
                        let apiData = self.state.apiData
                        apiData.templateResult.path = text
                        self.setState({apiData:apiData})
                    })
                }
            )
        }

        return false;
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
                                                    title: 'Edit',
                                                    url: `/generator/${this.props.match.params.id}/edit/files/${this.props.match.params.fileId}`
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
                                        <h1>Edit file</h1>
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

                                                    <div className={'row'}>
                                                        <div className={'col'}>
                                                            <div className="form-group">
                                                                <label htmlFor="inpTitle">Path</label>

                                                                <input type="text" className="form-control"
                                                                       id="inpTitle1"
                                                                       name={'path1'}
                                                                       onChange={this.onChangePath}
                                                                       value={this.state.viewData.viewResult ? this.state.apiData.templateResult.path : this.state.apiData.file.path}/>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className={'row'}>
                                                        <div className={'col-6'}>
                                                            <div className="form-group">
                                                                <label htmlFor="exampleFormControlFile1">Change
                                                                    File</label>
                                                                <input type="file" className="form-control-file"
                                                                       id="exampleFormControlFile1"
                                                                       name="fileList2"
                                                                       onChange={this.onChange}/>
                                                            </div>
                                                        </div>
                                                        <div className={'col-6'}>
                                                            <div className="form-group float-right">
                                                                <br/>
                                                                <button type="submit"
                                                                        className="btn btn-primary ">Submit
                                                                </button>
                                                            </div>

                                                        </div>
                                                    </div>


                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br/>

                                <div className="container-fluid">
                                    <div className="row">
                                        <div className="col-sm-12">
                                            <div className="card">
                                                <div className="card-body">

                                                    <div className="form-group">
                                                        <label htmlFor="txtDescription">File Content</label>


                                                        <textarea className="form-control" id="txtDescription"
                                                                  style={{minHeight: '30vh'}}
                                                                  name={'description1'} disabled={true}
                                                                  value={this.state.viewData.viewResult == false ? this.state.apiData.file.content : this.state.apiData.templateResult.content}/>
                                                    </div>

                                                    <div className="form-group text-center">
                                                        <button onClick={this.onClickViewResult}
                                                                className={'btn btn-primary'}>
                                                            {this.state.viewData.viewResult == false? "View Render" : "View Content"}
                                                        </button> <a href={'https://mustache.github.io/'} target={'_blank'} className={'btn btn-default'}>
                                                            Mustache Docs
                                                        </a>

                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </main>
                    </div>
                </div>


            </section>
        );
    }
}


export default withRouter(EditFileListScene);
