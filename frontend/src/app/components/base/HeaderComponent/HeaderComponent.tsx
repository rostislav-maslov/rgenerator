import React from 'react';
import {Link as LinkRouter} from "react-router-dom";

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import AddIcon from '@material-ui/icons/Add';
import Link from '@material-ui/core/Link';

import useStyles from "./HeaderStyles";
import HeaderProps from "./HeaderProps";

import {HOST} from "../../../../gateways/services/Const"
import TokenRepository from "../../../../gateways/services/TokenRepository";

interface Props {
}

const LoggedComponent: React.FC<HeaderProps> = () => {
    const classes = useStyles();

    return (
        <div>
            <Button href={'/generator/create'} className={classes.buttons} color="primary"
                    startIcon={<AddIcon/>} variant="contained">
                CREATE
            </Button>
            <Button className={classes.buttons}>
                <Link onClick={() => {
                    TokenRepository.clean();
                    window.location.href = "/";
                }}>LOGOUT</Link>
            </Button>
        </div>
    )
}

const NotLoggedComponent: React.FC<HeaderProps> = () => {
    const classes = useStyles();

    return (
        <div>
            <Button className={classes.buttons}>
                <Link href={'/login'}>LOGIN</Link>
            </Button>
        </div>
    )
}

const HeaderComponent: React.FC<HeaderProps> = (props: HeaderProps) => {
    const classes = useStyles();
    const loggedUser = TokenRepository.getCurrentDeveloper() != null;

    return (
        <AppBar className={classes.appBar}>
            <Toolbar>
                <div className={classes.appBarLogo}>
                    RGenerator
                </div>

                <Link href={'/'} className={classes.linkFirst}>
                    Home
                </Link>
                <Link href={'/explore'} className={classes.link}>
                    Explore
                </Link>
                <Link href={'/how-to'} className={classes.link}>
                    How to
                </Link>
                <Link href={'/contact'} className={classes.link}>
                    Contact
                </Link>
                <Link href={'https://github.com/rostislav-maslov/rgenerator/issues'} className={classes.link}>
                    Bug tracker
                </Link>
                <Link href={HOST + '/swagger-ui.html'} className={classes.link} target={'_blank'}>
                    API
                </Link>

                <section className={classes.rightToolbar}>
                    {loggedUser === true ? (<LoggedComponent/>) : (<NotLoggedComponent/>)}
                </section>
            </Toolbar>
        </AppBar>
    )
}

export default HeaderComponent;

