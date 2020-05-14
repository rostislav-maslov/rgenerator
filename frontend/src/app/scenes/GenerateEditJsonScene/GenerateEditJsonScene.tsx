import React, {Component} from 'react';

import {Link, withRouter} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";

import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";

import {toast} from 'react-toastify';
import PropsType from "./GenerateEditJsonProps";
import StateType from "./GenerateEditJsonState";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import InfoComponent from "../../components/generator/InfoComponent";
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import JsonComponent from "../../components/generator/JsonComponent";
import JsonStringComponent from "../../components/generator/JsonStringComponent";
import Typography from '@material-ui/core/Typography';


class GenerateEditJsonScene extends Component<PropsType, StateType> {
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
                    title: '',
                    id: null
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

    loadGenerator = (id: String) => {
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

    onSubmit = (e: any) => {
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
                toast.success("Your generator did update", {
                    position: toast.POSITION.BOTTOM_RIGHT
                });
            })
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

    render() {
        return (
            <section>
                <Breadcrumbs links={[
                    {title: 'Home', url: '/'},
                    {title: 'Explore', url: '/explore'},
                    {
                        title: this.state.apiData.generator.title,
                        url: `/generator/${this.props.match.params.id}`
                    },
                    {
                        title: 'Edit Json',
                        url: `/generator/${this.props.match.params.id}/json`,
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
                            <LeftMenuComponent {...generateLeftMenuProps('json', this.state.apiData.generator)}/>
                        </Grid>
                        <Grid item xs={12} md={8} lg={9}>
                            <form onSubmit={this.onSubmit}>
                                <Typography variant="h3" component="h1">
                                    {this.state.apiData.generator.title}
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


                                </Grid>

                                <br/>
                                <br/>

                                <Button type='submit' size="large" color="primary" variant="contained"
                                        fullWidth>
                                    SUBMIT
                                </Button>

                            </form>
                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}


export default withRouter(GenerateEditJsonScene);
