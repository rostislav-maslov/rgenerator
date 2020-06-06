import React, {Component} from 'react';

import {withRouter} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import PropsType from "../GenerateEditJsonScene/GenerateEditJsonProps";
import StateType from "../GenerateEditJsonScene/GenerateEditJsonState";
import DirFilesComponent from "../../components/generator/DirFilesComponent/DirFilesComponent";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';


class GenerateEditFileAddScene extends Component<PropsType, StateType> {
    _input: any = null;

    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
            },
            apiData: {
                generator: {
                    title: '',
                    id: null,
                    files: []
                },
                templateResult: null
            },
            data: {
                filesToUpload: []
            }
        };
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        this.update()
        this._input.directory = true;
        this._input.webkitdirectory = true;
    }

    update = () => {
        this.loadGenerator(this.props.match.params.id)
    }

    loadGenerator = (id: string) => {
        let self = this
        GeneratorApi.findById(id)
            .then((result) => {
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
    }

    onSubmit = (e: any) => {
        e.preventDefault()
        this.uploadNextFile()
        return false
    }

    uploadNextFile = () => {
        let data = this.state.data
        let generator = this.state.apiData.generator
        let self = this
        let hasFile = false

        for (let i = 0; i < data.filesToUpload.length; i++) {
            if (data.filesToUpload[i].didUpload == false) {
                hasFile = true
                GeneratorApi.uploadFile(
                    generator.id,
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
            window.location.href = `/generator/${generator.id}/edit/files`
        }
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
                        title: 'Files',
                        url: `/generator/${this.props.match.params.id}/edit/files`
                    },
                    {
                        title: 'Add',
                        url: `/generator/${this.props.match.params.id}/edit/files/add`
                    },
                ]}/>
                <section style={{padding: '24px'}}>
                    <Grid container spacing={3}
                          direction="row"
                          justify="flex-start"
                          alignItems="flex-start"
                    >
                        <Grid item xs={12} md={4} lg={3}>
                            <LeftMenuComponent {...generateLeftMenuProps('files', this.state.apiData.generator)}/>
                        </Grid>
                        <Grid item xs={12} md={8} lg={9}>

                            <Typography variant="h3" component="h1">
                                Add files
                            </Typography>
                            <br/>
                            <br/>
                            <form onSubmit={this.onSubmit}>

                                <Paper style={{padding: '16px'}}>
                                    <Typography variant="h5" component="h5">
                                        Choose dir with template
                                    </Typography>
                                    <br/>
                                    <br/>
                                    <input type="file" className="form-control-file"
                                           id="exampleFormControlFile1"
                                           name="fileList2" dir={''}
                                           ref={i => this._input = i}
                                           onChange={this.onChange}/>
                                    <br/>
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
                                <br/>
                                <br/>

                                <Button type='submit' size="large" color="primary" variant="contained"
                                        fullWidth>SUBMIT</Button>

                            </form>

                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}


export default withRouter(GenerateEditFileAddScene);
