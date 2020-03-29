import React from 'react';
import logo from '../logo.svg';
import './App.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import Home from "../scenes/home";
import Create from "../scenes/create";
import TemplateResult from "../scenes/templateResult";
import EditInfo from "../scenes/editInfo";
import EditJson from "../scenes/editJson";
import EditFileList from "../scenes/editFileList";
import EditFileEdit from "../scenes/editFileEdit";
import EditFileAdd from "../scenes/editFileAdd";
import GenerateList from "../scenes/list";
import Header from "../components/Header";
import Footer from "../components/Footer";
import ViewScene from "../scenes/view";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

function App() {
    return (
        <Router>
            <Header/>
            <div className={'content'}>

                <Switch >
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
                    <Route path="/generator" >
                        <GenerateList/>
                    </Route>
                    <Route path="/">
                        <Home/>
                    </Route>
                </Switch>

            </div>
            <ToastContainer />
            <Footer/>
        </Router>
    );
}


export default App;
