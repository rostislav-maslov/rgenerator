import React, {Component} from 'react';

import {Link, withRouter} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";

import {toast} from 'react-toastify';
import PropsType from "./GenerateEditInfoProps";
import StateType from "./GenerateEditInfoState";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import Grid from '@material-ui/core/Grid';
import InfoComponent from "../../components/generator/InfoComponent";
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class GenerateEditInfoScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
                accessLevel: "",
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

    loadGenerator = (id: string) => {
        let self = this
        GeneratorApi.findById(id)
            .then((result: any) => {
                let apiData = this.state.apiData
                apiData.generator = result.result
                let viewData = this.state.viewData
                viewData.title = apiData.generator.title
                viewData.description = apiData.generator.description
                viewData.accessLevel = apiData.generator.accessLevel
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
            .updateInfo(this.state.apiData.generator.id, this.state.viewData.title, this.state.viewData.description, this.state.viewData.accessLevel)
            .then((result) => {
                toast.success("Your generator did update", {
                    position: toast.POSITION.BOTTOM_RIGHT
                })
            }).catch(() => {
                toast.error("You don't have access to edit this RGenerator", {
                    position: toast.POSITION.BOTTOM_RIGHT
                });
        });
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
                    {title: 'Explore', url: '/explore'},
                    {
                        title: this.state.apiData.generator.title,
                        url: `/generator/${this.props.match.params.id}`
                    },
                    {
                        title: 'Edit Info',
                        url: `/generator/${this.props.match.params.id}/info`,
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
                            <LeftMenuComponent {...generateLeftMenuProps('info', this.state.apiData.generator)}/>
                        </Grid>
                        <Grid item xs={12} md={8} lg={9}>

                            <Typography variant="h3" component="h1">
                                Edit Info
                            </Typography>
                            <br/>
                            <br/>
                            <form onSubmit={this.onSubmit}>

                                <InfoComponent
                                    title={this.state.viewData.title}
                                    description={this.state.viewData.description}
                                    accessLevel={this.state.viewData.accessLevel}
                                    onChangeAccessLevel={this.onChangeInput}
                                    onChangeTitle={this.onChangeInput}
                                    onChangeDescription={this.onChangeInput}/>

                                <br/>
                                <br/>

                                <Button type='submit' size="large" color="primary" variant="contained"
                                        fullWidth>SUBMIT</Button>

                            </form>

                        </Grid>
                    </Grid>
                </section>
            </section>


        );
    }
}


export default withRouter(GenerateEditInfoScene);
