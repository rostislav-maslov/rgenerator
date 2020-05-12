import React from 'react'
import HowItWorksProps from "./HowItWorksProps";
import HowItWorksStyles from "./HowItWorksStyles";
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';


const HowItWorksComponent:React.FC<HowItWorksProps> = (props: HowItWorksProps) => {
    let classes = HowItWorksStyles();
    return (
        <Paper className={classes.content}>
            <Typography variant={'h2'} className={classes.title}>
                How it works?
            </Typography>

            <div>
                <iframe className={classes.videoFrame} src="https://www.youtube.com/embed/5qap5aO4i9A"  allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" ></iframe>
            </div>
        </Paper>
    );
}

export default HowItWorksComponent