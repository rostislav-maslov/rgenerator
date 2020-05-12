import React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link as LinkRouter
} from "react-router-dom";

import Home from "../scenes/HomeScene";
import Create from "../scenes/CreateScene";
import TemplateResult from "../scenes/templateResult";
import EditInfo from "../scenes/editInfo";
import EditJson from "../scenes/editJson";
import EditFileList from "../scenes/editFileList";
import EditFileEdit from "../scenes/editFileEdit";
import EditFileAdd from "../scenes/editFileAdd";
import ExploreScene from "../scenes/ExploreScene";
import LoginScene from "../scenes/LoginScene";
import SignUpScene from "../scenes/SignUpScene";


import Header from "../components/base/HeaderComponent";
import Footer from "../components/base/FooterComponent";
import ViewScene from "../scenes/GeneratorViewScene/GeneratorViewScene";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

import CssBaseline from '@material-ui/core/CssBaseline';
import {makeStyles, createMuiTheme, ThemeProvider} from '@material-ui/core/styles';
import primaryColor from '@material-ui/core/colors/green';

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
                            <Route path="/generator/create">
                                <Create/>
                            </Route>
                            <Route path="/generator/:id/template-result">
                                <TemplateResult/>
                            </Route>
                            <Route path="/generator/:id/edit/info">
                                <EditInfo/>
                            </Route>
                            <Route path="/generator/:id/edit/json">
                                <EditJson/>
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
