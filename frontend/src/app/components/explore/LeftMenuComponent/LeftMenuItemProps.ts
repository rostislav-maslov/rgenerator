export default interface LeftMenuItemProps{
    key: string,
    title: string,
    link: string,

    showBadge?: boolean,
    badge?:string,

    showPlusButton?: boolean,

    isActive?: boolean
}