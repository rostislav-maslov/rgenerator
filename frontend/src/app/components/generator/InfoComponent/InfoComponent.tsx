import React from 'react'
import InfoProps from "./InfoProps";

import Input from '@material-ui/core/Input'

const InfoComponent: React.FC<InfoProps> = (props: InfoProps) => {
    let title = ''
    let description = ''
    let onChangeInput = () => {}
    return (
        <div>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">Info</h5>
                    <div className="form-group">
                        <label htmlFor="inpTitle">Title</label>
                               <Input type={'text'}/>

                    </div>
                    <div className="form-group">
                        <label htmlFor="txtDescription">Description</label>
                        <Input type={'textarea'}/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default InfoComponent