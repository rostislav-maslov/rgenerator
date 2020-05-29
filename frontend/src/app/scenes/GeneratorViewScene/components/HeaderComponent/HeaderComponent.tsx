import React from 'react';

import HeaderProps from "./HeaderProps";
import HeaderStyles from "./HeaderStyles";

const HeaderComponent: React.FC<HeaderProps> = (props: HeaderProps) => {
    const classes = HeaderStyles()

    return (
        <div>
            <h1 className={classes.title}>{props.title}</h1>
            <p className={classes.description}>{props.description}</p>
        </div>
    );
}


export default HeaderComponent;
