import React from 'react'
import BannerProps from "./BannerProps";
import BannerStyles from "./BannerStyles";

const BannerComponent: React.FC<BannerProps> = (props: BannerProps) => {
    let classes = BannerStyles()
    return (
        <div className={classes.container}>
           <div className={classes.title}>Best way
               improve your code</div>
           <div className={classes.subTitle}>Stop doing stupid work</div>
        </div>
    )
}

export default BannerComponent