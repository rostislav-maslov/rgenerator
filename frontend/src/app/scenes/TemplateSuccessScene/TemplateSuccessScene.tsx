import React, {Component} from 'react';

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";
import TemplateResultApi from "../../../gateways/services/TemplateResult";
import {withRouter} from 'react-router-dom';
import {FILE, FILE_ATTACH} from "../../../gateways/services/Const";
import ReactJson from 'react-json-view'
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import PropsType from "./TemplateSuccessProps";
import StateType from "./TemplateSuccessState";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import JsonStringComponent from "../../components/generator/JsonStringComponent";
import JsonComponent from "../../components/generator/JsonComponent";
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Modal from '@material-ui/core/Modal';
import {toast} from 'react-toastify';
import Paper from '@material-ui/core/Paper';
import LinearProgress from '@material-ui/core/LinearProgress';
import ImgVk from './img/iconfinder_vk_834714.png';
import ImgFb from './img/iconfinder_facebook_834722.png';
import ImgYT from './img/iconfinder_youtube3_834710.png';
import ImgIG from './img/iconfinder_instagram2_834717.png';
import ImgTW from './img/iconfinder_twitter_834708.png';

import ImgTeamRM from './img/team/MainPhoto.png'
import ImgTeamVK from './img/team/kP-k7pcngtc.png'
import ImgTeamAT from './img/team/IMG_20200218_132913_934.png'
import ImgTeamVB from './img/team/IMG_0959.png'
import ImgTeamVN from './img/team/eJul60OGc0c.png'

import ImgPatronsDS from './img/patrons/ds.png'
import ImgPatronsEK from './img/patrons/ek.png'
import ImgPatronsMK from './img/patrons/mk.png'
import ImgPatronsVK from './img/patrons/vk.png'
import ImgPatronsVS from './img/patrons/vs.png'

class TemplateSuccessScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
                modalOpen: false,
                progress: 0
            },
            apiData: {
                generator: {
                    id: null,
                    title: '',
                },
                templateResult: null
            },
            data: {}
        };
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        this.loadGenerator(this.props.match.params.id)
        this.update()
    }

    update = () => {
        this.timerProgress()
    }

    timerProgress = () => {
        setTimeout(() => {
            if (this.state.viewData.progress < 100) {
                this.upProgress()
                this.timerProgress()
            }
        }, 100)
    }

    upProgress = () => {
        let max = 4
        let rundom = Math.floor(Math.random() * Math.floor(max));
        let progress = this.state.viewData.progress
        progress = progress + rundom
        let viewData = this.state.viewData
        viewData.progress = progress
        this.setState({viewData: viewData})
    }

    loadGenerator = (id: String) => {
        let self = this
        GeneratorApi.findById(id)
            .then((response) => {
                if (response.ok === false) {
                    toast.error("You don't have access to view this RGenerator", {
                        position: toast.POSITION.BOTTOM_RIGHT
                    });
                    return;
                }


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


    generate = () => {
        let self = this
        TemplateResultApi.create(
            this.state.apiData.generator.id,
            JSON.stringify(this.state.viewData.example)
        ).then((result) => {
            result.json().then((responseJson) => {
                const json = responseJson.result
                let apiData = self.state.apiData
                let viewData = self.state.viewData
                viewData.modalOpen = true
                apiData.templateResult = json
                self.setState({apiData: apiData, viewData: viewData})
            })
        })
    }

    onSubmit = (e: any) => {
        e.preventDefault();
        this.generate()
        return false;
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
                    {title: 'Generators', url: '/generator'},
                    {
                        title: this.state.apiData.generator.title,
                        url: `/generator/${this.props.match.params.id}`
                    },
                    {
                        title: 'Generate',
                        url: `/generator/${this.props.match.params.id}/template-result`
                    },
                ]}/>
                <section style={{padding: '24px'}}>
                    <Grid container spacing={3}
                          direction="row"
                          justify="flex-start"
                          alignItems="flex-start"
                    >
                        <Grid item xs={12} md={4} lg={3}>
                            <LeftMenuComponent {...generateLeftMenuProps('generate', this.state.apiData.generator)}/>
                        </Grid>
                        <Grid item xs={12} md={8} lg={9}>
                            <form onSubmit={this.onSubmit}>
                                <Typography variant="h3" component="h1">
                                    Your template almost ready
                                </Typography>
                                <br/>
                                <br/>
                                <Grid container spacing={3}
                                      direction="row"
                                      justify="flex-start"
                                      alignItems="flex-start"
                                >
                                    <Grid item xs={12} md={12} lg={12}>
                                        <Paper style={{padding: '16px'}}>
                                            <Typography variant="h5" component="h5">
                                                Generate progress:
                                            </Typography>
                                            <br/>
                                            {this.state.viewData.progress < 100 ? (
                                                <div>
                                                    <LinearProgress variant="determinate" value={this.state.viewData.progress}/>
                                                </div>
                                            ) : (
                                                <div>

                                                    ✅ Result zip - <a href={FILE_ATTACH(this.props.match.params.templateId)} target={'_blank'}>zip-archive</a>
                                                </div>
                                            )}

                                            <br/>
                                        </Paper>
                                    </Grid>
                                </Grid>
                                <Grid container spacing={3}
                                      direction="row"
                                      justify="flex-start"
                                      alignItems="flex-start"
                                >
                                    <Grid item xs={12} md={6} lg={6}>
                                        <Paper style={{padding: '16px'}}>
                                            <Typography variant="h5">
                                                Become a Patron
                                            </Typography>
                                            <Typography variant="body2">
                                                <br/>
                                                We are small team who want upgrade code speed and remove unnecessary
                                                work from your life.
                                                <br/>
                                                <br/>
                                                We spent our time and money for work on this project. It’s make us
                                                happy, but we need your help 🤖
                                                <br/>
                                                <br/>
                                                <a href={'https://www.patreon.com/user?u=36747772&fan_landing=true'}>
                                                    <img
                                                        style={{height: '32px'}}
                                                        src={'https://c5.patreon.com/external/logo/become_a_patron_button.png'}/>
                                                </a>
                                            </Typography>
                                        </Paper>
                                    </Grid>

                                    <Grid item xs={12} md={6} lg={6}>
                                        <Paper style={{padding: '16px'}}>
                                            <Typography variant="h5">
                                                Share
                                            </Typography>
                                            <iframe width={'100%'} style={{border: 'none'}}
                                                    src="https://www.youtube.com/embed/5qap5aO4i9A"
                                                    allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture">
                                            </iframe>
                                            <br/>
                                            <br/>
                                            <a href={'https://vk.com/share.php?url=https%3A//www.youtube.com/watch%3Fv%3DvzzKj49RZAw%26feature%3Dshare'}
                                               target={'_blank'}>
                                                <img src={ImgVk} style={{width: '24px', marginRight: '8px'}}/>
                                            </a>
                                            <a href={'https://www.facebook.com/dialog/share?app_id=87741124305&href=https%3A//www.youtube.com/watch%3Fv%3DvzzKj49RZAw%26feature%3Dshare&display=popup'}
                                               target={'_blank'}>
                                                <img src={ImgFb} style={{width: '24px', marginRight: '8px'}}/>
                                            </a>
                                            <a href={'https://www.youtube.com/embed/5qap5aO4i9A'} target={'_blank'}>
                                                <img src={ImgYT} style={{width: '24px', marginRight: '8px'}}/>
                                            </a>
                                            <a href={'https://vk.com/share.php?url=https%3A//www.youtube.com/watch%3Fv%3DvzzKj49RZAw%26feature%3Dshare'}
                                               target={'_blank'}>
                                                <img src={ImgTW} style={{width: '24px', marginRight: '8px'}}/>
                                            </a>
                                        </Paper>
                                    </Grid>


                                </Grid>
                                <Grid container spacing={3}
                                      direction="row"
                                      justify="flex-start"
                                      alignItems="flex-start"
                                >
                                    <Grid item xs={12} md={6} lg={6}>
                                        <Paper style={{padding: '16px'}}>
                                            <Typography variant="h5">
                                                Our Patrons:
                                            </Typography>
                                            <br/>
                                            <br/>
                                            <div style={{
                                                backgroundImage: "url('" + ImgPatronsDS + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgPatronsEK + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgPatronsMK + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgPatronsVK + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgPatronsVS + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                        </Paper>
                                    </Grid>
                                    <Grid item xs={12} md={6} lg={6}>
                                        <Paper style={{padding: '16px'}}>
                                            <Typography variant="h5">
                                                Our team:
                                            </Typography>
                                            <br/>
                                            <br/>
                                            <div style={{
                                                backgroundImage: "url('" + ImgTeamRM + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgTeamVK + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgTeamVN + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgTeamVB + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                            <div style={{
                                                backgroundImage: "url('" + ImgTeamAT + "')",
                                                width: '32px',
                                                height: '32px',
                                                backgroundSize: 'cover',
                                                backgroundPosition: 'center',
                                                borderRadius: '16px',
                                                display: 'inline-block',
                                                marginRight: '8px'
                                            }}></div>
                                        </Paper>
                                    </Grid>
                                </Grid>
                            </form>
                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}


export default withRouter(TemplateSuccessScene);
