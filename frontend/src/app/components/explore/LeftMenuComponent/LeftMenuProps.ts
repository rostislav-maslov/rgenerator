import LeftMenuGroupProps from "./LeftMenuGroupProps";
import LeftMenuItemProps from "./LeftMenuItemProps";

interface LeftMenuProps {
    activeLink?: string,
    groups: LeftMenuGroupProps[]
}

function item(key: string, title: string, link: string, activeLink: string): LeftMenuItemProps {
    let item = {
        key: key,
        title: title,
        link: link,
        showBadge: false,
        showPlusButton: false,
        isActive: key == activeLink
    } as LeftMenuItemProps

    return item;
}

function itemBadge(key: string, title: string, link: string, activeLink: string, badge:string): LeftMenuItemProps {
    let itemObj:LeftMenuItemProps = item(key, title, link, activeLink)

    itemObj.showBadge = true
    itemObj.badge = badge

    return itemObj;
}

function itemPlusBtn(key: string, title: string, link: string, activeLink: string): LeftMenuItemProps {
    let itemObj:LeftMenuItemProps = item(key, title, link, activeLink)

    itemObj.showPlusButton = true

    return itemObj;
}

export const generateLeftMenuProps = (activeLink: string, generator: any) => {
    let menu: LeftMenuProps = {
        activeLink: activeLink,
        groups: [
            {
                items: [
                    itemBadge('home', '🏠 My Generators', '/', activeLink, "5"),
                    item('explore', '🕵 Explore', '/explore', activeLink),
                    item('top', '🔥 Top', '/top', activeLink),
                    item('favorite', '⭐ Favorite', '/top', activeLink),
                ]
            }
        ]
    }

    if(generator != null ) {
        menu.groups.push({
            items: [
                itemPlusBtn('---', generator.title, '/generator', activeLink),
                item('view', '👁️ View', '/generator', activeLink),
                item('info', 'ℹ️ Info', '/generator', activeLink),
                item('json', '📝 JSON Data', '/generator', activeLink),
                item('files', '📁 Files', '/generator', activeLink),
                item('generate', '🤖 GENERATE', '/generator', activeLink),
            ]
        })
    }

    return menu;
}

export default LeftMenuProps;