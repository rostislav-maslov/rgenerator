import React, {Component} from 'react';

import {withRouter} from "react-router-dom";

import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import PropsType from "./HowToProps";
import StateType from "./HowToState";
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import HowItWorksComponent from "../../components/mainPage/HowItWorksComponent";
import { Helmet } from 'react-helmet';


class EditFileListScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {};
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        this.update()
    }

    update = () => {
    }

    render() {
        return (
            <section>
                <Helmet>
                    <title>How To :: RGenerator</title>
                </Helmet>
                <Breadcrumbs links={[
                    {title: 'Home', url: '/'},
                    {title: 'How to', url: '/how-to'}
                ]}/>
                <section style={{padding: '24px'}}>
                    <Grid container spacing={3}
                          direction="row"
                          justify="flex-start"
                          alignItems="flex-start"
                    >

                        <Grid item xs={12} md={2} lg={2}></Grid>
                        <Grid item xs={12} md={8} lg={8}>
                            <HowItWorksComponent  video={'https://www.youtube.com/embed/uwf6zFPcNog'}/><br/>
                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}


export default withRouter(EditFileListScene);
