import React, {ReactNode} from 'react';

import Typography from '@material-ui/core/Typography'
import Link from '@material-ui/core/Link'
import {makeStyles, createMuiTheme, ThemeProvider} from '@material-ui/core/styles';

interface Props {
}

const useStyles = makeStyles((theme) => ({
    footer: {
        backgroundColor: 'white',
        height: '56px',
        paddingTop: '16px',
        bottom: '0px',
        left: '0px',
        width:'100%'
    },

    copyright: {
        fontSize: '16px',
        textAlign: 'center',
        letterSpacing: '0.15px',
        color: '#000000'
    }
}));

const FooterComponent: React.FC<Props> = (props: Props) => {
    const classes = useStyles();
    return (
        <footer className={classes.footer}>
            <Copyright/>
        </footer>
    );
}

export default FooterComponent;

function Copyright() {
    const classes = useStyles();

    return (
        <div className={classes.copyright}>
            {'Copyright © maslov.tech & FoodTech Lab '}
            {new Date().getFullYear()}
            {'.'}
        </div>
    );
}
