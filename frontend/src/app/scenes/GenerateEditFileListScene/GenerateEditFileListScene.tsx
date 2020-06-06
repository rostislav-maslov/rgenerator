import React, {Component} from 'react';

import {Link, withRouter} from "react-router-dom";

import GeneratorApi from "../../../gateways/services/Generator";
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import PropsType from "../GenerateEditJsonScene/GenerateEditJsonProps";
import StateType from "../GenerateEditJsonScene/GenerateEditJsonState";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import Grid from '@material-ui/core/Grid';
import TableContainer from '@material-ui/core/TableContainer';
import Paper from '@material-ui/core/Paper';
import TableHead from '@material-ui/core/TableHead';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableBody from '@material-ui/core/TableBody';
import Typography from '@material-ui/core/Typography';
import DeleteIcon from '@material-ui/icons/Delete'
import EditIcon from '@material-ui/icons/Edit'
import IconButton from '@material-ui/core/IconButton';
import AddBoxIcon from '@material-ui/icons/AddBox';
import Button from '@material-ui/core/Button';
import {Alert} from '@material-ui/lab';
import {toast} from 'react-toastify';
import TokenRepository from "../../../gateways/services/TokenRepository";
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import GithubConnectorComponent from "../../components/generator/GithubConnectorComponent/GithubConnectorComponent";
import DividerComponent from "../../components/base/DividerComponent";


class GenerateEditFileListScene extends Component<PropsType, StateType> {
    constructor(props: any) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
                selectedRepo: null
            },
            apiData: {
                generator: {
                    title: '',
                    id: null,
                    files: []
                },
                repos: [],
                templateResult: null
            },
            data: {}
        };
    }

    componentDidMount() {
        window.scrollTo(0, 0)
        this.update()
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

    onDeleteFile = (e: any, fileId: string) => {
        e.preventDefault();
        let self = this
        GeneratorApi.deleteFile(this.state.apiData.generator.id, fileId).then((response) => {
            self.update()
        }, (reason: any) => {
            toast.error("You don't have access to edit this RGenerator", {
                position: toast.POSITION.BOTTOM_RIGHT
            })
        })
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


                            <Grid container spacing={3}
                                  direction="row"
                                  justify="flex-start"
                                  alignItems="flex-start"
                            >
                                <Grid item xs={9} md={8} lg={8}>
                                    <Typography variant="h3" component="h1">
                                        {this.state.apiData.generator.title}
                                    </Typography>
                                </Grid>
                                <Grid item xs={12} md={4} lg={4} alignItems={'flex-end'} style={{textAlign: 'right'}}>
                                    <Button variant="contained" color="primary"
                                            href={`/generator/${this.props.match.params.id}/edit/files/add`}
                                            size="large" startIcon={<AddBoxIcon/>}>
                                        Upload files
                                    </Button>
                                </Grid>
                            </Grid>
                            <br/>

                            <br/>
                            <GithubConnectorComponent generatedId={this.props.match.params.id}
                                                      generatorDidUpdate={() => this.loadGenerator(this.props.match.params.id)}/>

                            <DividerComponent text={'OR EDIT'}/>

                            <TableContainer component={Paper}>
                                <Table aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell align="center">#</TableCell>
                                            <TableCell>Path</TableCell>
                                            <TableCell align="right">Actions</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {this.state.apiData.generator.files.map((file: any, idx: number) => {
                                            return (
                                                <TableRow key={idx}>
                                                    <TableCell component="th" scope="row" align="center">
                                                        {idx + 1}
                                                    </TableCell>
                                                    <TableCell>
                                                        {file.path}
                                                    </TableCell>
                                                    <TableCell align="right">
                                                        <Link
                                                            to={`/generator/${this.props.match.params.id}/edit/files/${file.fileId}`}
                                                            className={'btn btn-sm btn-primary'}>
                                                            <IconButton color="primary" aria-label="Edit">
                                                                <EditIcon/>
                                                            </IconButton>
                                                        </Link>

                                                        <span onClick={(e) => {
                                                            this.onDeleteFile(e, file.fileId)
                                                        }}>
                                                            <IconButton color="secondary" aria-label="Delete">
                                                                <DeleteIcon/>
                                                            </IconButton>
                                                        </span>

                                                    </TableCell>
                                                </TableRow>
                                            )
                                        })}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </Grid>
                    </Grid>
                </section>
            </section>
        );
    }
}


export default withRouter(GenerateEditFileListScene);
