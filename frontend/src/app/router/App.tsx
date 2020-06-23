import React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link as LinkRouter
} from "react-router-dom";

import Home from "../scenes/HomeScene";
import GenerateCreateScene from "../scenes/GenerateCreateScene";
import TemplateResult from "../scenes/TemplateResultScene";
import GenerateEditInfoScene from "../scenes/GenerateEditInfoScene";
import GenerateEditJsonScene from "../scenes/GenerateEditJsonScene";
import EditFileList from "../scenes/GenerateEditFileListScene";
import EditFileEdit from "../scenes/GeneratorEditFileEditScene";
import EditFileAdd from "../scenes/GenerateEditFileAddScene";
import ExploreScene from "../scenes/ExploreScene";
import MyGeneragorsScene from "../scenes/MyGeneragorsScene";
import SignUpScene from "../scenes/SignUpScene";
import RecoverScene from "../scenes/RecoverScene";


import Header from "../components/base/HeaderComponent";
import Footer from "../components/base/FooterComponent";
import ViewScene from "../scenes/GeneratorViewScene/GeneratorViewScene";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

import CssBaseline from '@material-ui/core/CssBaseline';
import {makeStyles, createMuiTheme, ThemeProvider} from '@material-ui/core/styles';
import primaryColor from '@material-ui/core/colors/green';
import ForgotScene from "../scenes/ForgotScene";
import LoginScene from "../scenes/LoginScene";
import GithubConnectScene from "../scenes/GithubConnectScene/GithubConnectScene";
import HowToScene from "../scenes/HowToScene/HowToScene";
import TemplateSuccessScene from "../scenes/TemplateSuccessScene/TemplateSuccessScene";

interface Props {

}

const theme = createMuiTheme({
    typography: {
        fontFamily: [
            '-apple-system',
            'Montserrat',
            '"Segoe UI"',
            'Roboto',
            '"Helvetica Neue"',
            'Arial',
            'sans-serif',
            '"Apple Color Emoji"',
            '"Segoe UI Emoji"',
            '"Segoe UI Symbol"',
        ].join(','),
    },
    palette: {
        primary: primaryColor,
    },


});

const useStyles = makeStyles((theme) => ({

    main: {
        marginTop: '64px',
        minHeight: 'calc( 100vh - 70px - 56px)',
        position:'relative'
    },

}));

const App: React.FC<Props> = (props: Props) => {
    const classes = useStyles(theme);

    return (
        <React.Fragment>
            <ThemeProvider theme={theme}>
                <CssBaseline/>
                <Header/>
                <main className={classes.main}>
                    <Router>

                        <Switch>
                            <Route path="/login">
                                <LoginScene/>
                            </Route>
                            <Route path="/sign-up">
                                <SignUpScene/>
                            </Route>
                            <Route path="/forgot/:code">
                                <RecoverScene/>
                            </Route>
                            <Route path="/forgot">
                                <ForgotScene/>
                            </Route>
                            <Route path="/generator/create">
                                <GenerateCreateScene/>
                            </Route>
                            <Route path="/generator/:id/template-result/:templateId">
                                <TemplateSuccessScene/>
                            </Route>
                            <Route path="/generator/:id/template-result">
                                <TemplateResult/>
                            </Route>
                            <Route path="/generator/:id/edit/info">
                                <GenerateEditInfoScene/>
                            </Route>
                            <Route path="/generator/:id/edit/json">
                                <GenerateEditJsonScene/>
                            </Route>
                            <Route path="/generator/:id/edit/files/add">
                                <EditFileAdd/>
                            </Route>
                            <Route path="/generator/:id/edit/files/:fileId">
                                <EditFileEdit/>
                            </Route>
                            <Route path="/generator/:id/edit/files">
                                <EditFileList/>
                            </Route>
                            <Route path="/generator/:id">
                                <ViewScene/>
                            </Route>
                            <Route path="/explore">
                                <ExploreScene/>
                            </Route>
                            <Route path="/my-rgenerators">
                                <MyGeneragorsScene/>
                            </Route>
                            <Route path="/github/callback">
                                <GithubConnectScene/>
                            </Route>
                            <Route path="/how-to">
                                <HowToScene/>
                            </Route>
                            <Route path="/">
                                <Home/>
                            </Route>
                        </Switch>

                    </Router>
                </main>
                <ToastContainer/>
                {/* Footer */}
                <Footer/>
                {/* End footer */}
            </ThemeProvider>
        </React.Fragment>
    );
}




export default App;
