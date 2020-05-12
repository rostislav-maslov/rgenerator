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
        <Paper className={classes.paper}>
            <form>
            <TextField id="outlined-basic" label="Email" variant="outlined" required fullWidth margin="normal"/>
            <TextField id="outlined-basic" label="Password" variant="outlined" type={'password'} required fullWidth margin="normal"/>
            <Button className={classes.submit} type='submit' size="large" color="primary" variant="contained" fullWidth >SIGN UP</Button>
            </form>
        </Paper>
    );
}

export default SignUpComponent