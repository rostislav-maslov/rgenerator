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


class EditJsonScene extends Component {
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
                    id: null
                },
                templateResult: null
            },
            data: {}
        };
    }

    componentDidMount() {
        window.scrollTo(0,0)
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
        e.preventDefault();
        this.save()
        return false;
    }

    save = () => {
        let request = {}
        let self = this
        GeneratorApi
            .updateJson(this.state.apiData.generator.id, this.state.viewData.exampleString)
            .then((result) => {
                toast.success("Your generator did update",{
                    position: toast.POSITION.BOTTOM_RIGHT
                });
            })
    }

    onUpdateJson = (e) => {
        let viewData = this.state.viewData;
        viewData.example = e.updated_src
        viewData.exampleString = JSON.stringify(e.updated_src, null, 4)
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
                        <LeftMenuComponent  activeLink={'json'} generator={this.state.apiData.generator}/>

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
                                                    title: 'Edit Json',
                                                    url: `/generator/${this.props.match.params.id}/json`
                                                },
                                            ]}/>
                                        </div>
                                        <div className={'col-2'}>
                                            <Link to={`/generator/${this.props.match.params.id}/template-result`}
                                                  style={{
                                                      padding: '.69rem 1rem',
                                                      fontSize: '.875rem'
                                                  }}
                                                  className={'btn btn-primary btn-md float-right'}>
                                                Generate
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

                            <form onSubmit={this.onSubmit}>
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

                                <div className="container">
                                    <div className="row">
                                        <div className="col text-center">
                                            <br/>
                                            <br/>
                                            <button type="submit" className="btn btn-primary ">Submit</button>
                                            <br/>
                                            <br/>
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


export default withRouter(EditJsonScene);
