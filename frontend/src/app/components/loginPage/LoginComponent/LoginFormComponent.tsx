import React from 'react'
import LoginProps from "./LoginProps";
import Button from '@material-ui/core/Button';
import LoginFormStyles from "./LoginFormStyles";
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';

function loginRender(props: any, classes: any) {
    return (
        <div>
            <TextField id="outlined-basic" label="Email" variant="outlined" required fullWidth={true}
                       margin="normal"
                       onChange={(event) => {
                           event.preventDefault()
                           props.onChange!("email", event.target.value)
                       }}/>
            <br/>
            <TextField id="outlined-basic" label="Password" variant="outlined" type={'password'} required
                       fullWidth={true}
                       margin="normal"
                       onChange={(event) => {
                           event.preventDefault()
                           props.onChange!("password", event.target.value)
                       }}/>
            <br/>
            <br/>
            <Button className={classes.submit} type='submit' size="large" color="primary" variant="contained"
                    fullWidth>
                SUBMIT
            </Button>
            <br/>
            <br/>
            <div>
                <Button className={classes.submit} type='submit' href={'/sign-up'}>
                    Sign Up
                </Button>
                <Button className={classes.submit} type='submit' href={'/forgot'}>
                    Forgot password?
                </Button>
            </div>
        </div>
    );
}

function signUpRender(props: any, classes: any) {
    return (
        <div>
            <TextField id="outlined-basic" label="Email" variant="outlined" required fullWidth={true}
                       margin="normal"
                       onChange={(event) => {
                           event.preventDefault()
                           props.onChange!("email", event.target.value)
                       }}/>

            <div>
                <br/>
                <TextField id="outlined-basic" label="Login" variant="outlined" required fullWidth={true}
                           margin="normal"
                           onChange={(event) => {
                               event.preventDefault()
                               props.onChange!("login", event.target.value)
                           }}/>
            </div>
            <br/>
            <TextField id="outlined-basic" label="Password" variant="outlined" type={'password'} required
                       fullWidth={true}
                       margin="normal"
                       onChange={(event) => {
                           event.preventDefault()
                           props.onChange!("password", event.target.value)
                       }}/>
            <br/>
            <br/>
            <Button className={classes.submit} type='submit' size="large" color="primary" variant="contained"
                    fullWidth>
                SUBMIT
            </Button>
            <br/>
            <br/>
            <div>
                <Button className={classes.submit} type='submit' href={'/login'}>
                    Login
                </Button>
                <Button className={classes.submit} type='submit' href={'/forgot'}>
                    Forgot password?
                </Button>
            </div>
        </div>
    );
}

function recoverRender(props: any, classes: any) {
return (
    <div>
        <TextField id="outlined-basic" label="Email" variant="outlined" required fullWidth={true}
                   margin="normal"
                   onChange={(event) => {
                       event.preventDefault()
                       props.onChange!("email", event.target.value)
                   }}/>

        <br/>
        <TextField id="outlined-basic" label="New Password" variant="outlined" type={'password'} required
                   fullWidth={true}
                   margin="normal"
                   onChange={(event) => {
                       event.preventDefault()
                       props.onChange!("password", event.target.value)
                   }}/>
        <br/>
        <br/>
        <Button className={classes.submit} type='submit' size="large" color="primary" variant="contained"
                fullWidth>
            SUBMIT
        </Button>
        <br/>
        <br/>
        <div>
            <Button className={classes.submit} type='submit' href={'/login'}>
                Login
            </Button>
            <Button className={classes.submit} type='submit' href={'/sign-up'}>
                Sign Up
            </Button>
        </div>
    </div>
);
}

function forgotRender(props: any, classes: any) {
    return (
        <div>
            <TextField id="outlined-basic" label="Email" variant="outlined" required fullWidth={true}
                       margin="normal"
                       onChange={(event) => {
                           event.preventDefault()
                           props.onChange!("email", event.target.value)
                       }}/>

            <br/>


            <Button className={classes.submit} type='submit' size="large" color="primary" variant="contained"
                    fullWidth>
                SUBMIT
            </Button>
            <br/>
            <br/>
            <div>
                <Button className={classes.submit} type='submit' href={'/login'}>
                    Login
                </Button>
                <Button className={classes.submit} type='submit' href={'/sign-up'}>
                    Sign Up
                </Button>
            </div>
        </div>
    );
}

const LoginFormComponent: React.FC<LoginProps> = (props: LoginProps) => {
    let classes = LoginFormStyles()

    let title = 'Sign Up'
    if (props.type === 'login') title = 'Login'
    if (props.type === 'forgot') title = 'Enter email'
    if (props.type === 'recover') title = 'Enter new password'

    return (
        <div className={classes.container}>
            <form onSubmit={props.onSubmit}>
                <Typography variant="h4" component="h1">
                    {title}
                </Typography>

                {props.type === 'login' ? loginRender(props, classes): false}
                {props.type === 'signup' ? signUpRender(props, classes): false}
                {props.type === 'forgot' ? forgotRender(props, classes): false}
                {props.type === 'recover' ? recoverRender(props, classes): false}
            </form>

        </div>
    )
}

export default LoginFormComponent