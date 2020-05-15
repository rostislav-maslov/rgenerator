import React, {Component} from 'react';

import LoginFormComponent from '../../components/loginPage/LoginComponent'
import BannerComponent from "../../components/loginPage/BannerComponent";

import {withRouter} from "react-router-dom";
import PropsType from "./SignUpProps";
import StateType from "./SignUpState";
import Grid from '@material-ui/core/Grid';
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";

class SignUpScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {},
            apiData: {},
            data: {}
        };
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        this.update()
    }

    update = () => {
    }

    onSubmit = (e: any) => {
        e.preventDefault();
        this.save()
        return false;
    }

    save = () => {

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
                <Grid container spacing={0}
                      direction="row"
                      justify="flex-start"
                      alignItems="flex-start"
                >

                    <Grid item xs={12} md={6} lg={6}>
                        <BannerComponent/>
                    </Grid>
                    <Grid item xs={12} md={6} lg={6}>
                        <LoginFormComponent type={'signup'}/>
                    </Grid>

                </Grid>


            </section>
        );
    }
}


export default withRouter(SignUpScene);
