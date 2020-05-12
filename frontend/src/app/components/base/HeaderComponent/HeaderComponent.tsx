import React from 'react';
import {Link as LinkRouter} from "react-router-dom";

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import AddIcon from '@material-ui/icons/Add';
import Link from '@material-ui/core/Link';

import useStyles from "./HeaderStyles";
import HeaderProps from "./HeaderProps";

interface Props {
}

const HeaderComponent: React.FC<HeaderProps> = (props: HeaderProps) => {
    const classes = useStyles();

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

                <section className={classes.rightToolbar}>
                    <Button className={classes.buttons} color="primary" startIcon={<AddIcon />} variant="contained">
                        CREATE
                    </Button>
                    <Button className={classes.buttons}>
                        <Link href={'/login'}>LOGIN</Link>
                    </Button>
                </section>
            </Toolbar>
        </AppBar>
    )
}

export default HeaderComponent;

