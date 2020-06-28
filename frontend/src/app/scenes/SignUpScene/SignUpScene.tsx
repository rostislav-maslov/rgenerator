import React, {Component} from 'react';
import PropsType from "./SignUpProps";
import StateType from "./SignUpState";
import Grid from '@material-ui/core/Grid';
import BannerComponent from "../../components/loginPage/BannerComponent";
import LoginFormComponent from "../../components/loginPage/LoginComponent";
import AuthApi from "../../../gateways/services/Auth"
import TokenRepository from "../../../gateways/services/TokenRepository"
import DeveloperApi from "../../../gateways/services/Developer"
import { Helmet } from 'react-helmet';

class SignUpScene extends Component<PropsType, StateType> {
    _input: any = null

    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                email: '',
                login: '',
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

    signUp = () => {
        AuthApi.signUp(this.state.viewData.email!, this.state.viewData.login!, this.state.viewData.password!).then((result) => {
            TokenRepository.setAccessToken(result.result.accessToken)
            TokenRepository.setRefreshToken(result.result.refreshToken)

            this.updateCurrentDeveloper()
        }, (reason) => {
            alert("User already exist");
            return
        })
    }


    onSubmit = (event: any) => {
        event.preventDefault()
        this.signUp()
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
                    <title>Sign Up :: RGenerator</title>
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
                        <LoginFormComponent type={'signup'} onChange={this.onChangeInput} onSubmit={this.onSubmit}/>
                    </Grid>

                </Grid>


            </section>
        );
    }
}

export default SignUpScene;