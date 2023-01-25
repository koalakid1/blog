import {writable} from "svelte/store";
import {base} from "./utils/api";

const createBoards = () => {
    const {subscribe, set, update} = writable([])

    const get = async () => {
        const res = await base().get("/boards")
        set(await res.data)
    }

    get()

    return {
        subscribe,
        get
    }
}
export const boards = createBoards();