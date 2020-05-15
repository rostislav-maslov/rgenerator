import React from 'react'
import JsonProps from "./JsonProps";
import JsonStyles from "./JsonStyles";

import Input from '@material-ui/core/Input'
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import ReactJson from 'react-json-view'

const JsonComponent: React.FC<JsonProps> = (props: JsonProps) => {
    let classes = JsonStyles()

    return (
        <Paper className={classes.paper}>
            <h5  className={classes.title}>Or just create new one</h5>
            <br/>
            <ReactJson
                style={{minHeight: '50vh'}}
                src={props.example}
                collapsed={false}
                enableClipboard={true}
                onEdit={props.onUpdateJson}
                onAdd={props.onUpdateJson}
                onDelete={props.onUpdateJson}
            />
        </Paper>
    )
}

export default JsonComponent