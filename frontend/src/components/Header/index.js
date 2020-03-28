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
        <nav className="navbar navbar-expand-lg navbar-light bg-light fixed-top">
            <div className="container">
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarTogglerDemo03"
                        aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <Link to="/" className={'navbar-brand'}>FoodTech</Link>

                <div className="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
                        <li className="nav-item active">
                            <Link to="/list" className={'nav-link'}>Каталог</Link>
                        </li>
                        <li className="nav-item">
                            <Link to="/404" className={'nav-link'}>Информация о задачах и помощь</Link>
                        </li>
                        <li className="nav-item">
                            <a href="https://food-api.maslov.tech/static/docs/asciidoc/index.html" className={'nav-link'}>API Docs</a>
                        </li>
                    </ul>
                    <form className="form-inline my-2 my-lg-0">
                        <Link to="/basket" className={'nav-link'}>Корзина</Link>
                        <Link to="/client" className={'nav-link'}>Личный кабинет</Link>
                    </form>
                </div>
            </div>
        </nav>
    );
}

export default Header;
