import React, {Component} from 'react';

import GeneratorApi from "../../../../gateways/services/Generator";
import DeveloperApi from "../../../../gateways/services/Developer";
import PropsType from "./GithubConnectorProps";
import StateType from "./GithubConnectorState";
import Button from '@material-ui/core/Button';
import {Alert} from '@material-ui/lab';
import {toast} from 'react-toastify';
import TokenRepository from "../../../../gateways/services/TokenRepository";
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';

class GithubConnectorComponent extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            showCheckAlert: false,
            selectedRepo: null,
            repos: [],
            exampleJson: true,
            rootDir: true
        };
    }

    componentDidMount() {
        this.update()
    }

    update = () => {
        this.updateDev()
        if (TokenRepository.getCurrentDeveloper()?.githubConnected == true) {
            this.loadRepos()
        }
    }

    updateDev = () => {
        DeveloperApi.me().then((response) => {
            if(response.ok == false) return;
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

    checkRepo = () => {
        GeneratorApi.check(this.state.selectedRepo).then(response => {
            response.json().then(result => {

                let json = result.result;
                let showCheckAlert = false;
                if (json.exampleJson == false || json.rootDir == false) {
                    showCheckAlert = true
                } else {
                    showCheckAlert = false
                }

                this.setState({
                    showCheckAlert: showCheckAlert,
                    exampleJson: json.exampleJson,
                    rootDir: json.rootDir
                })

            })
        })

    }

    loadRepos = () => {
        GeneratorApi.repos().then(response => {
            response.json().then(res => {
                const repos = res.result;
                this.setState({repos: repos})
            })
        })
    }

    onClickGithubConnect = () => {
        const clientId = '495f7822d3349cdceeea'
        const redirectUri = 'https://rgenerator.maslov.tech/github/callback'
        const scope = 'repo public_repo'
        window.location.href = `https://github.com/login/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&scope=${scope}`
    }

    onSync = () => {
        toast.info("Import did start", {
            position: toast.POSITION.BOTTOM_RIGHT
        })

        GeneratorApi.sync(this.props.generatedId, this.state.selectedRepo).then(response => {
            response.json().then(result => {
                toast.success("Content from GitHub did updated successfully", {
                    position: toast.POSITION.BOTTOM_RIGHT
                })
                this.props.generatorDidUpdate()
            })
        })
    }

    render() {
        return (
            <Paper style={{ padding: '24px 16px'}}>
                <Typography variant="h4" component="h4">
                    Import all data from GitHub repo
                </Typography>
                <br/>
                <br/>

                {TokenRepository.getCurrentDeveloper()?.githubConnected == true ? (
                    <div>
                        {this.state.exampleJson === false ? (
                            <div>
                                <Alert severity="warning" variant="filled">
                                    Can't find example.json in root directory. Take a look example of repository - <a
                                    href={'https://github.com/rostislav-maslov/rgenerator-template-example'} target={'_blank'}>rostislav-maslov/rgenerator-template-example</a>
                                </Alert>
                                <br/>
                            </div>
                        ) : false}
                        {this.state.rootDir === false ? (
                            <div>
                                <Alert severity="warning" variant="filled">
                                    Can't find root directory of template files. Take a look example of repository - <a
                                    href={'https://github.com/rostislav-maslov/rgenerator-template-example'} target={'_blank'}>rostislav-maslov/rgenerator-template-example</a>
                                </Alert>
                                <br/>
                            </div>
                        ) : false}

                        <Grid container spacing={3}
                              direction="row"
                              justify="flex-start"
                              alignItems="flex-start"
                        >
                            <Grid item xs={9} md={8} lg={8}>
                                <FormControl variant="outlined">
                                    <InputLabel htmlFor="outlined-age-native-simple">Repo</InputLabel>
                                    <Select

                                        native
                                        value={this.state.selectedRepo}
                                        onChange={(e) => {
                                            let selectedRepo = e.target.value
                                            this.setState({selectedRepo: selectedRepo}, this.checkRepo)
                                        }}
                                        label="Age"
                                        inputProps={{
                                            name: 'repo',
                                            id: 'outlined-age-native-simple',
                                        }}
                                    >
                                        <option>Choose repo for import</option>
                                        {this.state.repos.map((repo: any, idx: number) => {
                                            return (<option value={repo.full_name}>{repo.full_name}</option>)
                                        })}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={3} md={4} lg={4}>
                                {this.state.exampleJson === true && this.state.rootDir === true && this.state.selectedRepo != null ? (
                                    <Button size={"large"} variant="contained" color="primary" onClick={this.onSync}>
                                        Import files and JSON
                                    </Button>
                                ):(
                                    <Button size={"large"} variant="outlined" disabled={true}>
                                        Import files and JSON
                                    </Button>
                                )}

                            </Grid>
                        </Grid>


                    </div>
                ) : (
                    <Alert severity="info" variant="filled" action={
                        <Button color="inherit" size="medium" onClick={this.onClickGithubConnect}>
                            CONNECT GITHUB REPOSITORY
                        </Button>
                    }>
                        Connect github repo with all your files and JSON example. This way much more easy to use and
                        organize RGenerator content!
                    </Alert>
                )}
            </Paper>
        );
    }
}


export default GithubConnectorComponent;
