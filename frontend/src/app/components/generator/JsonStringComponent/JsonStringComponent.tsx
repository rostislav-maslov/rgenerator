import React from 'react'
import JsonStringProps from "./JsonStringProps";
import JsonStringStyles from "./JsonStringStyles";
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';

const JsonStringComponent: React.FC<JsonStringProps> = (props: JsonStringProps) => {
    let classes = JsonStringStyles()

    return (
        <Paper className={classes.paper}>
            <h5  className={classes.title}>Past example of your data</h5>

            <TextField  variant="outlined"  fullWidth margin="normal"
                       name={'example'}
                       multiline={true}
                       // rowsMin={10}
                       defaultValue=""
                       value={props.exampleString} onChange={props.onChangeExample}/>
        </Paper>
    )
}

export default JsonStringComponent;