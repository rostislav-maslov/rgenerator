import React from 'react'
import LoginProps from "./LoginProps";
import Button from '@material-ui/core/Button';
import LoginFormStyles from "./LoginFormStyles";
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';

const LoginFormComponent: React.FC<LoginProps> = (props: LoginProps) => {
    let classes = LoginFormStyles()

    let title = 'Sign Up'
    if (props.type === 'login') title = 'Login'
    if (props.type === 'forgot') title = 'Enter email'

    return (
        <div className={classes.container}>
            <form onSubmit={props.onSubmit}>
                <Typography variant="h4" component="h1">
                    {title}
                </Typography>
                <TextField id="outlined-basic" label="Email" variant="outlined" required fullWidth={true}
                           margin="normal"
                           onChange={(event) => {
                               event.preventDefault()
                               props.onChange!("email", event.target.value)
                           }}/>
                {props.type === 'signup' ?
                <div>
                    <br/>
                    <TextField id="outlined-basic" label="Login" variant="outlined" required fullWidth={true}
                               margin="normal"
                               onChange={(event) => {
                                   event.preventDefault()
                                   props.onChange!("login", event.target.value)
                               }}/>
                </div>
                    :false}

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
                {props.type === 'login' ? <div>
                    <Button className={classes.submit} type='submit' href={'/sign-up'}>
                        Sign Up
                    </Button>
                    {/*<Button className={classes.submit} type='submit' href={'/forgot'}>*/}
                    {/*    Forgot password?*/}
                    {/*</Button>*/}
                </div> : false}
                {props.type === 'signup' ? <div>
                    <Button className={classes.submit} type='submit' href={'/login'}>
                        Login
                    </Button>
                    {/*<Button className={classes.submit} type='submit' href={'/forgot'}>*/}
                    {/*    Forgot password?*/}
                    {/*</Button>*/}
                </div> : false}
                {props.type === 'forgot' ? <div>
                    <Button className={classes.submit} type='submit' href={'/login'}>
                        Login
                    </Button>
                    <Button className={classes.submit} type='submit' href={'/sign-up'}>
                        Sign Up
                    </Button>
                </div> : false}
            </form>

        </div>
    )
}

export default LoginFormComponent