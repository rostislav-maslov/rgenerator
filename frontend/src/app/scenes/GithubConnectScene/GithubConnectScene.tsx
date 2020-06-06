import React, {Component} from 'react';

import {withRouter} from "react-router-dom";
import PropsType from "./GithubConnectProps";
import StateType from "./GithubConnectState";
import DeveloperApi from "../../../gateways/services/Developer";
import TokenRepository, {Developer} from "../../../gateways/services/TokenRepository";


class GithubConnectScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {},
            apiData: {
                generator: {
                    title: '',
                    id: null,
                    files: []
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

    updateDev = () => {
        DeveloperApi.me().then((result) => {
            let developer = {
                email: result.result.email,
                id: result.result.id,
                login: result.result.login,
                githubConnected: result.result.githubConnected
            }

            TokenRepository.setCurrentDeveloper(developer)

            window.location.href = "/my-rgenerators"
        })

    }

    update = () => {
        const params = new URLSearchParams(this.props.location.search);
        const code = params.get('code');
        DeveloperApi.connectGithub(code!).then((response) => {
            this.updateDev()
        })
    }

    render() {
        return (
            <section>
                Connecting
            </section>
        );
    }
}


export default withRouter(GithubConnectScene);
