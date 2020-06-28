import React, {Component} from 'react';
import PropsType from "./LoginProps";
import StateType from "./LoginState";
import Grid from '@material-ui/core/Grid';
import BannerComponent from "../../components/loginPage/BannerComponent";
import LoginFormComponent from "../../components/loginPage/LoginComponent";
import AuthApi from "../../../gateways/services/Auth"
import TokenRepository from "../../../gateways/services/TokenRepository";
import DeveloperApi from "../../../gateways/services/Developer";
import { Helmet } from 'react-helmet';

class LoginScene extends Component<PropsType, StateType> {
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

    updateCurrentDeveloper = () => {
        DeveloperApi.me().then((result) => {
            let developer = {
                email: result.result.email,
                id: result.result.id,
                login: result.result.login,
                githubConnected: result.result.githubConnected
            }

            TokenRepository.setCurrentDeveloper(developer)

            window.location.href = '/explore'
        })
    }

    login = () => {
        AuthApi.login(this.state.viewData.email!, this.state.viewData.password!).then((result) => {
                TokenRepository.setAccessToken(result.result.accessToken)
                TokenRepository.setRefreshToken(result.result.refreshToken)

                this.updateCurrentDeveloper()
        }, (reason) => {
            alert("Check email and password");
            return
        })
    }


    onSubmit = (event: any) => {
        event.preventDefault()
        this.login()
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
                    <title>Login :: RGenerator</title>
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
                        <LoginFormComponent type={'login'} onChange={this.onChangeInput} onSubmit={this.onSubmit}/>
                    </Grid>

                </Grid>


            </section>
        );
    }
}

export default LoginScene;