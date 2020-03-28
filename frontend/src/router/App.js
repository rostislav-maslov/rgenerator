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
import GenerateList from "../scenes/list";
import Header from "../components/Header";
import Footer from "../components/Footer";

function App() {
    return (
        <Router>
            <Header/>
            <div className={'content'}>

                <Switch>
                    <Route path="/list">
                        <GenerateList/>
                    </Route>
                    <Route path="/template-result/:id">
                        <TemplateResult/>
                    </Route>
                    <Route path="/create">
                        <Create/>
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
