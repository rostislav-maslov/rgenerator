import React from 'react'
import DirFilesProps from "./DirFilesProps";
import DirFilesStyles from "./DirFilesStyles";
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';

const DirFilesComponent: React.FC<DirFilesProps> = (props: DirFilesProps) => {
    let classes = DirFilesStyles()

    return (
        <Paper className={classes.paper}>
            <h5 className={classes.title}>Choose dir with template</h5>

            <br/>
            <br/>
            <input type="file" className="form-control-file"
                   id="exampleFormControlFile1"
                   name="fileList2" dir={''}

                   onChange={props.onChange}/>
            <br/>
            <br/>


            {props.filesToUpload.length > 0 ?
                (
                    <div>
                        <h2>Files</h2>
                        <div>
                            {props.filesToUpload.map((file: any, idx: number) => {
                                return (<div key={idx}>[{file.didUpload.toString()}]
                                    - {file.path}</div>)
                            })}
                        </div>
                    </div>
                ) : false}
        </Paper>
    )
}

export default DirFilesComponent;