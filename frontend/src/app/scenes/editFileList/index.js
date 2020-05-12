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
            data: {}
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

    onDeleteFile = (e, fileId) => {
        e.preventDefault();
        let self = this
        GeneratorApi.deleteFile(this.state.apiData.generator.id, fileId).then(() => {
            self.update()
        })
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
                                        <h1>{this.state.apiData.generator.title}</h1>
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

                            <div className={'container-fluid'}>
                                <div className={'row'}>
                                    <div className={'col'}>
                                        <h2>Files list</h2>
                                        <div className="table-responsive">
                                            <table className="table table-striped table-sm">
                                                <thead>
                                                <tr>
                                                    <th style={{width: '30px'}}>#</th>
                                                    <th>Path</th>
                                                    <th style={{width: '160px'}}>Actions</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                {this.state.apiData.generator.files.map((file, idx) => {
                                                    return (<tr key={idx}>
                                                        <td>{idx + 1}</td>
                                                        <td>{file.path}</td>
                                                        <td>
                                                            <Link to={`/generator/${this.props.match.params.id}/edit/files/${file.fileId}`} className={'btn btn-sm btn-primary'}>
                                                                edit
                                                            </Link> <button className={'btn btn-sm btn-danger'} onClick={(e) => {
                                                                this.onDeleteFile(e, file.fileId)
                                                            }}>
                                                                delete
                                                            </button>
                                                        </td>
                                                    </tr>)
                                                })}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>


                        </main>
                    </div>
                </div>


            </section>
        );
    }
}


export default withRouter(EditFileListScene);
