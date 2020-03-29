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
import Edit from "../scenes/edit";
import GenerateList from "../scenes/list";
import Header from "../components/Header";
import Footer from "../components/Footer";
import ViewScene from "../scenes/view";


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
                    <Route path="/generator/:id/edit">
                        <Edit/>
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
            <Footer/>
        </Router>
    );
}


export default App;
