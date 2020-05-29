import React, {Component} from 'react';

import {withRouter} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import Grid from '@material-ui/core/Grid';
import CardLinkComponent from "../../components/generator/CardLinkComponent";
import PropsType from './GeneratorViewProps'
import StateType from './GeneratorViewState'
import HeaderComponent from "./components/HeaderComponent";
import {toast} from 'react-toastify';

class GeneratorViewScene extends Component<PropsType, StateType> {
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

                if(response.ok === false) {
                    toast.error("You don't have access to view this RGenerator", {
                        position: toast.POSITION.BOTTOM_RIGHT
                    });
                    return;
                }

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
                            <HeaderComponent title={this.state.viewData.title} description={this.state.viewData.description}/>

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
