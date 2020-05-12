import React from 'react';

import HeaderProps from "./HeaderProps";
import HeaderStyles from "./HeaderStyles";

const HeaderComponent: React.FC<HeaderProps> = (props: HeaderProps) => {
    const classes = HeaderStyles()

    return (
        <div>
            <div className="container-fluid">
                <h1 className={classes.title}>{props.title}</h1>
            </div>
            <div className="container-fluid">
                <p className={classes.description}>{props.description}</p>
            </div>
        </div>
    );
}


export default HeaderComponent;
