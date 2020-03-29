import React from 'react';
import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

function Header() {
    return (

        <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
            <Link to={'/'} className="navbar-brand col-sm-3 col-md-2 mr-0">
                RGenerator
            </Link>
            {/*<input className="form-control form-control-dark w-100" type="text" placeholder="Search"*/}
            {/*       aria-label="Search"/>*/}
            <ul className="navbar-nav px-3">
                <li className="nav-item text-nowrap">
                    {/*<Link to="/list" className={'nav-link'}>List</Link>*/}
                    {/*<Link to="/Example" className={'nav-link'}>Example</Link>*/}
                    <Link to="/generator/create" className={'nav-link'}>Create</Link>
                </li>
            </ul>
        </nav>

        // <nav className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        //     <div className="container">
        //         <button className="navbar-toggler" type="button" data-toggle="collapse"
        //                 data-target="#navbarTogglerDemo03"
        //                 aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
        //             <span className="navbar-toggler-icon"></span>
        //         </button>
        //
        //         <Link to="/" className={'navbar-brand'}>RGenerator</Link>
        //
        //         <div className="collapse navbar-collapse" id="navbarTogglerDemo03">
        //             <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
        //
        //                 <li className="nav-item active">
        //
        //                 </li>
        //
        //                 <li className="nav-item active">
        //
        //                 </li>
        //                 {/*<li className="nav-item">*/}
        //                 {/*    <a href="https://food-api.maslov.tech/static/docs/asciidoc/index.html" className={'nav-link'}>API Docs</a>*/}
        //                 {/*</li>*/}
        //             </ul>
        //             <form className="form-inline my-2 my-lg-0">
        //                 {/*<Link to="/basket" className={'nav-link'}>Корзина</Link>*/}
        //                 {/*<Link to="/client" className={'nav-link'}>Личный кабинет</Link>*/}
        //
        //
        //
        //             </form>
        //         </div>
        //     </div>
        // </nav>
    );
}

export default Header;
