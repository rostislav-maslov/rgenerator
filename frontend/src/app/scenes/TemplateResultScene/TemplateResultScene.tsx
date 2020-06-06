import React, {Component} from 'react';

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
import PropsType from "../GeneratorEditFileEditScene/GeneratorEditFileEditProps";
import StateType from "../GeneratorEditFileEditScene/GeneratorEditFileEditState";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import JsonStringComponent from "../../components/generator/JsonStringComponent";
import JsonComponent from "../../components/generator/JsonComponent";
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Modal from '@material-ui/core/Modal';
import {toast} from 'react-toastify';
import TokenRepository from "../../../gateways/services/TokenRepository";

class TemplateResultScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
                modalOpen: false
            },
            apiData: {
                generator: {
                    id: null,
                    title: '',
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

    loadGenerator = (id: string) => {
        let self = this
        GeneratorApi.findById(id)
            .then((result: any) => {
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

            }, (reject: any) => {
                toast.error("You don't have access to view this RGenerator", {
                    position: toast.POSITION.BOTTOM_RIGHT
                });
                return;
            })
    }


    generate = () => {
        let self = this
        TemplateResultApi.create(
            this.state.apiData.generator.id,
            JSON.stringify(this.state.viewData.example)
        ).then((responseJson) => {
            const json = responseJson.result
            let apiData = self.state.apiData
            let viewData = self.state.viewData
            viewData.modalOpen = true
            apiData.templateResult = json

            window.location.href = '/generator/' + this.state.apiData.generator.id + '/template-result/' + json.resultFileId
        })
    }

    onSubmit = (e: any) => {
        e.preventDefault();
        this.generate()
        return false;
    }

    onUpdateJson = (e: any) => {
        let viewData = this.state.viewData;
        viewData.example = e.updated_src
        viewData.exampleString = JSON.stringify(e.updated_src, null, 4)
        this.setState({viewData: viewData})
    }

    onChangeExample = (e: any) => {
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

    onChangeInput = (e: any) => {
        const name = e.target.name;
        const value = e.target.value;

        let viewData = this.state.viewData;
        viewData[name] = value
        this.setState({viewData: viewData})
    }

    render() {
        return (
            <section>
                <Breadcrumbs links={[
                    {title: 'Home', url: '/'},
                    {title: 'Generators', url: '/generator'},
                    {
                        title: this.state.apiData.generator.title,
                        url: `/generator/${this.props.match.params.id}`
                    },
                    {
                        title: 'Generate',
                        url: `/generator/${this.props.match.params.id}/template-result`
                    },
                ]}/>
                <section style={{padding: '24px'}}>
                    <Grid container spacing={3}
                          direction="row"
                          justify="flex-start"
                          alignItems="flex-start"
                    >
                        <Grid item xs={12} md={4} lg={3}>
                            <LeftMenuComponent {...generateLeftMenuProps('generate', this.state.apiData.generator)}/>
                        </Grid>
                        <Grid item xs={12} md={8} lg={9}>
                            <form onSubmit={this.onSubmit}>
                                <Typography variant="h3" component="h1">
                                    Generate: {this.state.viewData.title}
                                </Typography>
                                <br/>
                                <br/>
                                <Grid container spacing={3}
                                      direction="row"
                                      justify="flex-start"
                                      alignItems="flex-start"
                                >

                                    <Grid item xs={12} md={6} lg={6}>
                                        <JsonStringComponent exampleString={this.state.viewData.exampleString}
                                                             onChangeExample={this.onChangeExample}
                                        />
                                    </Grid>

                                    <Grid item xs={12} md={6} lg={6}>
                                        <JsonComponent example={this.state.viewData.example}
                                                       onUpdateJson={this.onUpdateJson}
                                        />
                                    </Grid>

                                    <Grid item xs={12} alignContent={'center'}>
                                        {this.state.apiData.templateResult != null ? (
                                            <div>
                                                <br/>
                                                <br/>
                                                <Button type='submit' size="large" color="primary"
                                                        variant="contained" fullWidth>
                                                    Generate Template Result
                                                </Button>
                                                <br/>
                                                <br/>
                                                ZIP with your template: <a
                                                href={FILE_ATTACH(this.state.apiData.templateResult.resultFileId)}
                                                target={'_blank'}>
                                                download - {this.state.apiData.templateResult.resultFileId}
                                            </a>
                                                <br/>
                                                <br/>
                                            </div>
                                        ) : (
                                            <div>
                                                {TokenRepository.getCurrentDeveloper() == null ? (
                                                    <div>
                                                        <br/>
                                                        <br/>
                                                        <Button href={'/login'} size="large" color="secondary"
                                                                variant="contained"
                                                                fullWidth>
                                                            Please, login before use generator
                                                        </Button>
                                                        <br/>
                                                        <br/>
                                                    </div>
                                                ) : (
                                                    <div>
                                                        <br/>
                                                        <br/>
                                                        <Button type='submit' size="large" color="primary"
                                                                variant="contained"
                                                                fullWidth>
                                                            Generate Template
                                                            Result
                                                        </Button>
                                                        <br/>
                                                        <br/>
                                                    </div>
                                                )}

                                            </div>)}
                                    </Grid>
                                </Grid>
                                <br/>
                                <br/>
                            </form>
                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}


export default withRouter(TemplateResultScene);
