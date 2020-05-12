import React, {Component} from 'react';

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import {RouteComponentProps} from "react-router";
import GeneratorApi from "../../../gateways/services/Generator";
import TemplateResultApi from "../../../gateways/services/TemplateResult";
import {withRouter} from 'react-router-dom';
import {FILE, FILE_ATTACH} from "../../../gateways/services/Const";
import ReactJson from 'react-json-view'
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import LeftMenuGroupProps from "../../components/explore/LeftMenuComponent/LeftMenuGroupProps";
import LeftMenuItemProps from "../../components/explore/LeftMenuComponent/LeftMenuItemProps";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import GeneratorItemProps from "../../components/explore/GeneratorItemComponent/GeneratorItemProps";
import GeneratorItemComponent from "../../components/explore/GeneratorItemComponent";
import Grid from '@material-ui/core/Grid';
import GeneratorViewStyles from "./GeneratorViewStyles";
import CardLinkComponent from "../../components/generator/CardLinkComponent";

// Type whatever you expect in 'this.props.match.params.*'
type PathParamsType = {
    id: string,
}

// Your component own properties
type PropsType = RouteComponentProps<PathParamsType> & {
    someString?: string,
}

interface Stt {
    viewData: any,
    apiData: any,
    data: any
}

class GeneratorViewScene extends Component<PropsType, Stt> {
    constructor(props: any) {
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

    loadGenerator = (id: string) => {
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


    render() {


        return (
            <section>
                <Breadcrumbs links={[
                    {title: 'Home', url: '/'},
                    {title: 'Explore', url: '/explore'},
                    {
                        title: this.state.apiData.generator.title,
                        url: `/generator/${this.props.match.params.id}`,
                        active: true
                    },
                ]}/>
                <section style={{padding: '24px'}}>
                    <Grid container spacing={3}
                          direction="row"
                          justify="flex-start"
                          alignItems="flex-start"
                    >
                        <Grid item xs={12} md={4} lg={3}>
                            <LeftMenuComponent {...generateLeftMenuProps('view', this.state.apiData.generator)}/>
                        </Grid>
                        <Grid item xs={12} md={8} lg={9}>
                            <div className="container-fluid">
                                <div className={'row'}>
                                    <div className={'col'}>
                                        <h1>{this.state.viewData.title}</h1>
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

                            <Grid container spacing={1}
                                  direction="row"
                                  justify="flex-start"
                                  alignItems="flex-start"
                            >
                                <Grid item xs={12} md={3} lg={3}>
                                    <CardLinkComponent title={'Generate template'}
                                                       description={'Use this generator for create new Template Result'}
                                                       link={`/generator/${this.state.apiData.generator.id}/template-result`}/>
                                </Grid>
                                <Grid item xs={12} md={3} lg={3}>
                                    <CardLinkComponent title={'Edit info'}
                                                       description={'Edit info about your Generator'}
                                                       link={`/generator/${this.state.apiData.generator.id}/edit/info`}/>
                                </Grid>
                                <Grid item xs={12} md={3} lg={3}>
                                    <CardLinkComponent title={'Edit json data'}
                                                       description={'Edit JSON example, for your generator'}
                                                       link={`/generator/${this.state.apiData.generator.id}/edit/json`}/>
                                </Grid>
                                <Grid item xs={12} md={3} lg={3}>
                                    <CardLinkComponent title={'Edit files'}
                                                       description={'Edit files and test your generator'}
                                                       link={`/generator/${this.state.apiData.generator.id}/edit/files`}/>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}


export default withRouter(GeneratorViewScene);
