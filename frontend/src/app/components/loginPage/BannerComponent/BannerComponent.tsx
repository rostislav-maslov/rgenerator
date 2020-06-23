import React from 'react'
import BannerProps from "./BannerProps";
import BannerStyles from "./BannerStyles";

const BannerComponent: React.FC<BannerProps> = (props: BannerProps) => {
    let classes = BannerStyles()
    return (
        <div className={classes.container}>
           <div className={classes.title}>
               Best way<br/>
               improve your code
           </div>
           <div className={classes.subTitle}>
               RGenerator is a code generation development platform inspired by the way you work.
               Our target improve team work, code standards and speed of developers work.
           </div>
        </div>
    )
}

export default BannerComponent