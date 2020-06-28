import React, {Component} from 'react';
import PropsType from "./ForgotProps";
import StateType from "./ForgotState";
import Grid from '@material-ui/core/Grid';
import BannerComponent from "../../components/loginPage/BannerComponent";
import LoginFormComponent from "../../components/loginPage/LoginComponent";
import AuthApi from "../../../gateways/services/Auth"
import TokenRepository from "../../../gateways/services/TokenRepository";
import DeveloperApi from "../../../gateways/services/Developer";
import { Helmet } from 'react-helmet';

class ForgotScene extends Component<PropsType, StateType> {
    _input: any = null

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
        AuthApi.forgotPasswordStart(this.state.viewData.email!).then((result) => {
            alert("We send to you recovery link. Please, check email!");
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
                    <title>Forgot password :: RGenerator</title>
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
                        <LoginFormComponent type={'forgot'} onChange={this.onChangeInput} onSubmit={this.onSubmit}/>
                    </Grid>

                </Grid>


            </section>
        );
    }
}

export default ForgotScene;