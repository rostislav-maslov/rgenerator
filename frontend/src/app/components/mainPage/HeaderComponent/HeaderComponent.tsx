import React from 'react';
import HeaderProps from './HeaderProps'

import Typography from '@material-ui/core/Typography'
import SignUpComponent from "./SignUpComponent";
import HeaderStyles from "./HeaderStyles";
import Grid from '@material-ui/core/Grid';

const HeaderComponent: React.FC<HeaderProps> = (props: HeaderProps) => {
    let classes = HeaderStyles();
    return (
        <Grid className={classes.content}>
            <Grid
                container
                direction="row"
                justify="center"
                alignItems="center"
            >
                <Grid item xs={12} md={5}>
                    <Typography variant={'h1'} className={classes.titleDiv}>
                        Don’t copy
                        Generate it
                    </Typography>
                    <Typography variant={'body1'} className={classes.subtitleDiv}>
                        GitHub is a development platform inspired by the way you work.
                        From open source to business, you can host and review code, manage projects, and build software alongside 40 million developers.
                    </Typography>
                </Grid>
                <Grid item xs={12} md={3}>
                    <SignUpComponent/>
                </Grid>
            </Grid>


        </Grid>
    );
}

export default HeaderComponent