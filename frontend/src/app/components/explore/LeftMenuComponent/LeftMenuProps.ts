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
        isActive: key === activeLink
    } as LeftMenuItemProps

    return item;
}

function itemBadge(key: string, title: string, link: string, activeLink: string, badge: string): LeftMenuItemProps {
    let itemObj: LeftMenuItemProps = item(key, title, link, activeLink)

    itemObj.showBadge = true
    itemObj.badge = badge

    return itemObj;
}

function itemPlusBtn(key: string, title: string, link: string, activeLink: string): LeftMenuItemProps {
    let itemObj: LeftMenuItemProps = item(key, title, link, activeLink)

    itemObj.showPlusButton = true

    return itemObj;
}

export const generateLeftMenuProps = (activeLink: string, generator: any) => {
    let menu: LeftMenuProps = {
        activeLink: activeLink,
        groups: [
            {
                items: [
                    item('home', '🏠 My Generators', '/my-rgenerators', activeLink),
                    item('explore', '🕵 Explore', '/explore', activeLink),
                    // item('top', '🔥 Top', '/top', activeLink),
                    // item('favorite', '⭐ Favorite', '/top', activeLink),
                ]
            }
        ]
    }

    if (generator != null) {
        menu.groups.push({
            items: [
                itemPlusBtn('---', generator.title, '/generator/' + generator.id, activeLink),
                item('view', '👁️ View', '/generator/' + generator.id, activeLink),
                item('info', 'ℹ️ Info', '/generator/' + generator.id + '/edit/info', activeLink),
                item('json', '📝 JSON Data', '/generator/'+generator.id+'/edit/json', activeLink),
                item('files', '📁 Files', '/generator/'+generator.id+'/edit/files', activeLink),
                item('generate', '🤖 GENERATE', '/generator/'+generator.id+'/template-result', activeLink),
            ]
        })
    }

    return menu;
}

export default LeftMenuProps;