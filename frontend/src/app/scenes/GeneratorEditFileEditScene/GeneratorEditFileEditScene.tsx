import React, {Component} from 'react';

import {Link, withRouter} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import PropsType from "./GeneratorEditFileEditProps";
import StateType from "./GeneratorEditFileEditState";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import InfoComponent from "../../components/generator/InfoComponent";
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';


class EditFileListScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
                viewResult: false
            },
            apiData: {
                generator: {
                    title: '',
                    id: null,
                    files: []
                },
                file: {
                    fileId: null,
                    path: null,
                    content: null,
                    type: null
                },
                templateResult: {
                    path: '',
                    content: ''
                }
            },
            data: {
                file: null
            }
        };
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        this.update()
    }

    update = () => {
        this.loadGenerator(this.props.match.params.id)
    }

    loadGenerator = (id: String) => {
        let self = this
        GeneratorApi.findById(id)
            .then((response) => {
                response.json().then(result => {
                    let apiData = this.state.apiData
                    apiData.generator = result.result

                    let viewData = this.state.viewData
                    viewData.title = apiData.generator.title
                    viewData.description = apiData.generator.description

                    self.setState({
                        apiData: apiData,
                        viewData: viewData
                    }, () => {
                        self.loadFileContent(id, this.props.match.params.fileId)
                    })
                })
            })
    }

    loadFileContent = (id: String, fileId: String) => {
        let self = this
        GeneratorApi.fileContent(id, fileId).then((result) => {
            result.json().then((response) => {

                let apiData = this.state.apiData
                apiData.file = response.result

                self.setState({
                    apiData: apiData
                })
            })
        })
    }

    onChange = (e: any) => {
        const files = [...e.target.files]
        let fileToUpload = null
        let fileObj = files[0]

        fileToUpload = {
            path: fileObj.webkitRelativePath,
            file: fileObj,
            didUpload: false
        }

        let data = this.state.data
        data.file = fileToUpload
        this.setState({data: data})
    }

    uploadFile = () => {
        let data = this.state.data
        let apiData = this.state.apiData
        GeneratorApi.deleteFile(apiData.generator.id, apiData.file.fileId).then(
            (response) => {
                GeneratorApi.uploadFile(
                    apiData.generator.id,
                    apiData.file.path,
                    data.file.file
                ).then((response) => {
                    response.json().then((result) => {
                        window.location.href = `/generator/${apiData.generator.id}/edit/files/${result.result.fileId}`
                    })
                })
            }
        )
    }

    onChangePath = (e: any) => {
        let apiData = this.state.apiData
        apiData.file.path = e.target.value
        this.setState({apiData: apiData})
    }

    onSubmit = (e: any) => {
        e.preventDefault();
        this.uploadFile()
        return false;
    }

    onClickViewResult = (e: any) => {
        e.preventDefault();
        let viewData = this.state.viewData
        viewData.viewResult = !viewData.viewResult
        this.setState({viewData: viewData})

        let self = this

        if (viewData.viewResult == true) {
            GeneratorApi.generateExample(this.state.apiData.generator.id, this.state.apiData.file.content).then(
                (response) => {
                    response.text().then((text) => {
                        let apiData = self.state.apiData
                        apiData.templateResult.content = text
                        self.setState({apiData: apiData})
                    })
                }
            )

            GeneratorApi.generateExample(this.state.apiData.generator.id, this.state.apiData.file.path).then(
                (response) => {
                    response.text().then((text) => {
                        let apiData = self.state.apiData
                        apiData.templateResult.path = text
                        self.setState({apiData: apiData})
                    })
                }
            )
        }

        return false;
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
                        title: 'Edit',
                        url: `/generator/${this.props.match.params.id}/edit/files/${this.props.match.params.fileId}`
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
                                Edit file
                            </Typography>
                            <br/>
                            <br/>
                            <form onSubmit={this.onSubmit}>

                                <Paper style={{padding: '16px'}}>
                                    <TextField variant="outlined" fullWidth margin="normal"
                                               label={'Path'}
                                               type="text"
                                               name={'path1'}
                                               onChange={this.onChangePath}
                                               value={this.state.viewData.viewResult ? this.state.apiData.templateResult.path : this.state.apiData.file.path}
                                    />
                                    <br/>
                                    <label htmlFor="exampleFormControlFile1">Change
                                        File</label>
                                    <br/>
                                    <input type="file" className="form-control-file"
                                           id="exampleFormControlFile1"
                                           name="fileList2"
                                           onChange={this.onChange}/>
                                    <br/>
                                    <br/>
                                    <Button type='submit' size="large" color="primary"
                                            variant="contained"
                                            fullWidth>SUBMIT</Button>
                                </Paper>
                                <br/>
                                <Paper style={{padding: '16px'}}>


                                    <TextField variant="outlined" fullWidth margin="normal"
                                               name={'description1'}
                                               label={'File Content'}
                                               multiline={true}
                                               rowsMax={10}
                                               defaultValue=""
                                               disabled={true}
                                               value={this.state.viewData.viewResult == false ? this.state.apiData.file.content : this.state.apiData.templateResult.content}/>

                                    <div className="text-center">
                                        <Button onClick={this.onClickViewResult}>
                                            {this.state.viewData.viewResult == false ? "View Render" : "View Content"}
                                        </Button>
                                        <a href={'https://mustache.github.io/'} target={'_blank'}
                                           className={'btn btn-default'}>
                                            Mustache Docs
                                        </a>

                                    </div>


                                </Paper>

                                <br/>
                                <br/>


                            </form>

                        </Grid>
                    </Grid>
                </section>
            </section>


        );
    }
}


export default withRouter(EditFileListScene);
