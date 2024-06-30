import Vuex from 'vuex'
import state from "@/store/state.js";
import mutations from "@/store/mutations.js";
import actions from "@/store/actions.js";

export default function store() {
    return new Vuex.Store({
        state,
        mutations,
        actions,
    })
}


