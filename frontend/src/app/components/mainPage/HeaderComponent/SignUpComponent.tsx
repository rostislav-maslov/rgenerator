import React from 'react';

import SignUpProps from './SignUpProps'

import Typography from '@material-ui/core/Typography'
import Input from '@material-ui/core/Input'
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import SignUpStyles from "./SignUpStyles";
import TextField from '@material-ui/core/TextField';

const SignUpComponent: React.FC<SignUpProps> = (props: SignUpProps) => {
    let classes = SignUpStyles();
    return (
        <div className={classes.paper}>

        </div>
    );
}

export default SignUpComponent