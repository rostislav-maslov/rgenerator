import React, {Component} from 'react';

import ReactJson from 'react-json-view'

import GeneratorApi from "../../../gateways/services/Generator";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import PropsType from "./GenerateCreateProps";
import StateType from "./GenerateCreateState";
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import JsonStringComponent from "../../components/generator/JsonStringComponent";
import JsonComponent from "../../components/generator/JsonComponent";
import InfoComponent from "../../components/generator/InfoComponent";
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import TokenRepository from "../../../gateways/services/TokenRepository";
import { Helmet } from 'react-helmet';

class GenerateCreateScene extends Component<PropsType, StateType> {
    _input: any = null

    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: {},
                exampleString: "{}",
                accessLevel: 'PRIVATE'
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
        this.update()
        this._input.directory = true;
        this._input.webkitdirectory = true;

        if (TokenRepository.getCurrentDeveloper() === null) window.location.href = "/login";
    }

    update = () => {

    }


    onChange = (e: any) => {
        const files = [...e.target.files]
        let filesToUpload = this.state.data.filesToUpload

        files.forEach((file) => {
            filesToUpload.push({
                path: file.webkitRelativePath,
                file: file,
                didUpload: false
            })
        })

        let data = this.state.data
        data.filesToUpload = filesToUpload
        this.setState({data: data})
    }

    createGenerator = () => {
        let self = this
        GeneratorApi.create(
            this.state.viewData.title,
            this.state.viewData.description,
            JSON.stringify(this.state.viewData.example),
            this.state.viewData.accessLevel
        ).then((responseJson) => {
            const json = responseJson.result
            let data = this.state.data
            data.generator = json
            this.setState({data: data}, () => {
                self.uploadNextFile()
            })
        })
    }

    uploadNextFile = () => {
        let data = this.state.data
        let self = this
        let hasFile = false

        for (let i = 0; i < data.filesToUpload.length; i++) {
            if (data.filesToUpload[i].didUpload == false) {
                hasFile = true
                GeneratorApi.uploadFile(
                    data.generator.id,
                    data.filesToUpload[i].path,
                    data.filesToUpload[i].file
                ).then((response) => {
                    data.filesToUpload[i].didUpload = true
                    this.setState({data: data}, () => {
                        self.uploadNextFile()
                    })
                })
                break;
            }
        }

        if (hasFile == false) {
            // все файлы загрузились, нужно перейти на list
            window.location.href = `/generator/${data.generator.id}`
        }
    }

    onSubmit = (e: any) => {
        e.preventDefault();
        this.createGenerator()
        return false;
    }

    onUpdateJson = (e: any) => {
        let viewData = this.state.viewData;
        viewData.example = e.updated_src
        viewData.exampleString = JSON.stringify(e.updated_src, null, 4)
        this.setState({viewData: viewData})
    }

    onChangeInput = (e: any) => {
        const name = e.target.name;
        const value = e.target.value;

        let viewData = this.state.viewData;
        viewData[name] = value
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

    render() {
        return (
            <section>
                <Helmet>
                    <title>Create :: RGenerator</title>
                </Helmet>
                <Breadcrumbs links={[
                    {title: 'Home', url: '/'},
                    {title: 'Generators', url: '/generator'},
                    {title: 'Create new', url: '/generator/create'},
                ]}/>
                <section style={{padding: '24px'}}>
                    <Grid container spacing={3}
                          direction="row"
                          justify="flex-start"
                          alignItems="flex-start"
                    >
                        <Grid item xs={12} md={4} lg={3}>
                            <LeftMenuComponent {...generateLeftMenuProps('create', null)}/>
                        </Grid>
                        <Grid item xs={12} md={8} lg={9}>
                            <form onSubmit={this.onSubmit}>
                                <Typography variant="h3" component="h1">
                                    Create Generator
                                </Typography>
                                <br/>
                                <br/>
                                <Grid container spacing={3}
                                      direction="row"
                                      justify="flex-start"
                                      alignItems="flex-start"
                                >
                                    <Grid item xs={12} md={12} lg={12}>
                                        <InfoComponent
                                            title={this.state.viewData.title}
                                            description={this.state.viewData.description}
                                            accessLevel={this.state.viewData.accessLevel}
                                            onChangeTitle={this.onChangeInput}
                                            onChangeDescription={this.onChangeInput}
                                            onChangeAccessLevel={this.onChangeInput}/>
                                    </Grid>
                                    <Grid item xs={12} md={6} lg={6} style={{'display': 'none'}}>
                                        <JsonStringComponent exampleString={this.state.viewData.exampleString}
                                                             onChangeExample={this.onChangeExample}
                                        />
                                    </Grid>
                                    <Grid item xs={12} md={6} lg={6} style={{'display': 'none'}}>
                                        <JsonComponent example={this.state.viewData.example}
                                                       onUpdateJson={this.onUpdateJson}
                                        />
                                    </Grid>
                                </Grid>
                                <br/>
                                <br/>
                                <Grid item xs={12} md={12} lg={12} style={{'display': 'none'}}>
                                    <Paper style={{padding: '16px'}}>
                                        <h5 style={{fontSize: '32px', margin: '0px', fontWeight: 500}}>Choose dir with
                                            template</h5>
                                        <input type="file" className="form-control-file"
                                               id="exampleFormControlFile1"
                                               name="fileList2" dir={''}
                                               ref={i => this._input = i}
                                               onChange={this.onChange}/>
                                        <br/>

                                        {this.state.data.filesToUpload.length > 0 ?
                                            (
                                                <div>
                                                    <h2>Files</h2>
                                                    <div>
                                                        {this.state.data.filesToUpload.map((file: any, idx: number) => {
                                                            return (<div key={idx}>[{file.didUpload.toString()}]
                                                                - {file.path}</div>)
                                                        })}
                                                    </div>
                                                </div>
                                            ) : false}

                                    </Paper>
                                </Grid>

                                <br/>
                                <br/>

                                <Button type='submit' size="large" color="primary" variant="contained"
                                        fullWidth>
                                    SUBMIT
                                </Button>

                            </form>
                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}

export default GenerateCreateScene;