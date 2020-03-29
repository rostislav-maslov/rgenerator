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

class ViewScene extends Component {
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
                    id: null,
                    title: ''
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


    generate = () => {
        let self = this
        TemplateResultApi.create(
            this.state.apiData.generator.id,
            JSON.stringify(this.state.viewData.example)
        ).then((result) => {
            result.json().then((responseJson) => {
                const json = responseJson.result
                let apiData = self.state.apiData
                apiData.templateResult = json
                self.setState({apiData: apiData})
            })
        })
    }

    onSubmit = (e) => {
        e.preventDefault();
        this.generate()
        return false;
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

    onChangeInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;

        let viewData = this.state.viewData;
        viewData[name] = value
        this.setState({viewData: viewData})
    }

    renderCard(title, description, url){
        return ( <div className="card" style={{cursor:'pointer'}} onClick={() => {window.location = url}}>
            <div className="card-body" >
                <h5 className="card-title">{title}</h5>
                <p className="card-text">{description}</p>
            </div>
        </div>)
    }

    render() {
        return (
            <section>
                <div className="container-fluid">
                    <div className="row">
                        <LeftMenuComponent  activeLink={'view'} generator={this.state.apiData.generator}/>

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
                                            ]}/>
                                        </div>
                                        <div className={'col-2'}>
                                            <Link to={`/generator/${this.props.match.params.id}/template-result`}
                                                  style={{
                                                      padding: '.69rem 1rem',
                                                      fontSize: '.875rem'
                                                  }}
                                                  className={'btn btn-primary btn-md float-right'}>
                                                GENERATE
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            </section>

                            <div className="container-fluid">
                                <div className={'row'}>
                                    <div className={'col'}>
                                        <h1>{this.state.viewData.title}</h1>
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

                            <div className="container-fluid">
                                <div className={'row'}>
                                    <div className={'col'}>
                                        <p>{this.state.viewData.description}</p>
                                    </div>
                                </div>
                            </div>

                            <div className="container-fluid">
                                <div className={'row'}>
                                    <div className={'col-3'}>
                                        {this.renderCard('Generate template',
                                            'Use this generator for create new Template Result',
                                            `/generator/${this.state.apiData.generator.id}/template-result`
                                        )}

                                    </div>
                                    <div className={'col-3'}>
                                        {this.renderCard('Edit info', 'Edit info about your Generator',
                                            `/generator/${this.state.apiData.generator.id}/edit/info`)}

                                    </div>
                                    <div className={'col-3'}>
                                        {this.renderCard('Edit json data', 'Edit JSON example, for your generator',
                                            `/generator/${this.state.apiData.generator.id}/edit/json`)}
                                    </div>
                                    <div className={'col-3'}>
                                        {this.renderCard('Edit files', 'Edit files and test your generator',
                                            `/generator/${this.state.apiData.generator.id}/edit/files`)}
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


export default withRouter(ViewScene);
