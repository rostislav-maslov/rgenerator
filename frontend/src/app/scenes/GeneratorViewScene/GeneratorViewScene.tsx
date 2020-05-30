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
import DeleteIcon from '@material-ui/icons/Delete'
import IconButton from '@material-ui/core/IconButton';
import TokenRepository from "../../../gateways/services/TokenRepository";
import DeveloperApi from "../../../gateways/services/Developer";
import GithubConnectorComponent from "../../components/generator/GithubConnectorComponent";

class GeneratorViewScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
                isOwner: false
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
        this.updateDev()
    }

    updateDev = () => {
        DeveloperApi.me().then((response) => {
            response.json().then(result => {
                let developer = {
                    email: result.result.email,
                    id: result.result.id,
                    login: result.result.login,
                    githubConnected: result.result.githubConnected
                }

                TokenRepository.setCurrentDeveloper(developer)
            })
        })
    }

    loadGenerator = (id: string) => {
        let self = this
        GeneratorApi.findById(id)
            .then((response) => {

                if (response.ok === false) {
                    toast.error("You don't have access to view this RGenerator", {
                        position: toast.POSITION.BOTTOM_RIGHT
                    });
                    return;
                }

                response.json().then(result => {

                    const developer = TokenRepository.getCurrentDeveloper()
                    const ownerId = developer != null ? developer.id : null

                    let apiData = this.state.apiData
                    apiData.generator = result.result
                    let viewData = this.state.viewData
                    viewData.title = apiData.generator.title
                    viewData.description = apiData.generator.description
                    viewData.isOwner = apiData.generator.ownerId === ownerId
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

    deleteGenerator = () => {
        let self = this
        GeneratorApi.deleteById(this.props.match.params.id)
            .then((response) => {

                if (response.ok === false) {
                    toast.error("You don't have access to delete this RGenerator", {
                        position: toast.POSITION.BOTTOM_RIGHT
                    });
                    return;
                }

                window.location.href = '/my-rgenerators'
            })
    }

    onDelete = (e: any) => {
        e.preventDefault();

        const isConfirm = window.confirm("You going delete RGenerator with all data and template. Please confirm this action!")
        if (isConfirm == true) this.deleteGenerator();

        return false;
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
                            <Grid container spacing={1}
                                  direction="row"
                                  justify="flex-start"
                                  alignItems="flex-start"
                            >
                                <Grid item xs={10} md={10} lg={10}>
                                    <HeaderComponent title={this.state.viewData.title}
                                                     description={this.state.viewData.description}/>
                                </Grid>
                                <Grid item xs={2} md={2} lg={2} alignContent={'flex-end'} style={{textAlign: 'right'}}>
                                    {this.state.viewData.isOwner === true ? (
                                        <form onSubmit={this.onDelete}>
                                            <IconButton color="secondary" aria-label="Delete" type={'submit'}>
                                                <DeleteIcon/>
                                            </IconButton>
                                        </form>
                                    ) : false}
                                </Grid>
                            </Grid>

                            {this.state.apiData.generator.didUseGitHub === true ? (
                                <div></div>
                            ):(
                                <div>
                                    <br/>
                                    <GithubConnectorComponent generatedId={this.props.match.params.id} generatorDidUpdate={() => this.loadGenerator(this.props.match.params.id)}/>
                                    <br/>
                                </div>
                            )}


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
