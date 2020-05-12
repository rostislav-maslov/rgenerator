import React from 'react'
import BannerProps from "./BannerProps";

const BannerComponent: React.FC<BannerProps> = (props: BannerProps) => {
    return (
        <div>
           <div>Best way
               improve your code</div>
           <div>Stop doing stupid work</div>
        </div>
    )
}

export default BannerComponent