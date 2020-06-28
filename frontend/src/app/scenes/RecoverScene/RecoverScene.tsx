import React, {Component} from 'react';
import PropsType from "./RecoverProps";
import StateType from "./RecoverState";
import Grid from '@material-ui/core/Grid';
import BannerComponent from "../../components/loginPage/BannerComponent";
import LoginFormComponent from "../../components/loginPage/LoginComponent";
import AuthApi from "../../../gateways/services/Auth"
import { withRouter } from 'react-router-dom';
import { Helmet } from 'react-helmet';

class RecoverScene extends Component<PropsType, StateType> {

    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                email: '',
                password: '',
            },
            apiData: {
                catalogsResponse: [],
                productsResponse: []
            },
            data: {
                filesToUpload: [],
                generator: null
            }
        };
    }

    componentDidMount() {
        window.scrollTo(0, 0)
    }

    update = () => {

    }

    forgot = () => {
        AuthApi.forgotPasswordChange(this.state.viewData.email!, this.state.viewData.password!, this.props.match.params.code).then((result) => {
            window.location.href = '/login'
        }, (reason) => {
            alert("Check email and password");
            return
        })
    }


    onSubmit = (event: any) => {
        event.preventDefault()
        this.forgot()
        return false;
    }

    onChangeInput = (name: 'email' | 'login' | 'password', value: string) => {
        let viewData = this.state.viewData;
        viewData[name] = value
        this.setState({viewData: viewData})
    }

    render() {
        return (
            <section>
                <Helmet>
                    <title>Password recover :: RGenerator</title>
                </Helmet>
                <Grid container spacing={0}
                      direction="row"
                      justify="flex-start"
                      alignItems="flex-start"
                >

                    <Grid item xs={12} md={6} lg={6}>
                        <BannerComponent/>
                    </Grid>
                    <Grid item xs={12} md={6} lg={6}>
                        <LoginFormComponent type={'recover'} onChange={this.onChangeInput} onSubmit={this.onSubmit}/>
                    </Grid>

                </Grid>


            </section>
        );
    }
}

export default withRouter(RecoverScene);